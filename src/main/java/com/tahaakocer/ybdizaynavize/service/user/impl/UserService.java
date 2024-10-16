package com.tahaakocer.ybdizaynavize.service.user.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tahaakocer.ybdizaynavize.dto.user.UserDto;
import com.tahaakocer.ybdizaynavize.dto.user.response.LoginResponse;
import com.tahaakocer.ybdizaynavize.exception.user.EmailAlreadyExistsException;
import com.tahaakocer.ybdizaynavize.exception.user.UserNotFoundException;
import com.tahaakocer.ybdizaynavize.mapper.user.UserMapper;
import com.tahaakocer.ybdizaynavize.model.user.Role;
import com.tahaakocer.ybdizaynavize.model.user.User;
import com.tahaakocer.ybdizaynavize.repository.user.UserRepository;
import com.tahaakocer.ybdizaynavize.security.JwtService;
import com.tahaakocer.ybdizaynavize.security.TokenService;
import com.tahaakocer.ybdizaynavize.service.user.IUserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;

    public UserService(UserRepository userRepository, JwtService jwtService, TokenService tokenService, PasswordEncoder passwordEncoder, UserMapper userMapper, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.tokenService = tokenService;

        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.authenticationManager = authenticationManager;
    }
    @Override
    public UserDto register(UserDto userDTO) {
        // email zaten var mı kontrolü
        if (this.userRepository.existsByEmail(userDTO.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists");
        }
        // Şifreyi encode et
        userDTO.setPassword(this.passwordEncoder.encode(userDTO.getPassword()));

        // User DTO'yu User entity'e çevir
        User user = this.userMapper.userDtoToUser(userDTO);
//        TODO eğer çalışmazsa userdetails propları burada manuel eklenebilir..
        user.setAuthorities(new HashSet<>(Set.of(Role.ROLE_USER)));
        User savedUser = this.userRepository.save(user);

        return this.userMapper.userToUserDto(savedUser);

    }

    @Override
    public LoginResponse login(UserDto userDto) {
        LoginResponse response = new LoginResponse();
        try{
            User user = this.userRepository.findByEmail(userDto.getEmail()).orElseThrow(() -> new UserNotFoundException("User not found"));
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    userDto.getEmail(), userDto.getPassword()
            ));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtService.generateToken(authentication.getName());
            String refreshToken = jwtService.generateRefreshToken(authentication.getName());
            this.tokenService.saveAccessToken(user.getEmail(), jwt);
            this.tokenService.saveRefreshToken(user.getEmail(), refreshToken);
            response.setStatusCode(200);
            response.setToken(jwt);
            response.setId(user.getId());
            response.setRefreshToken(refreshToken);
            response.setAuthorities(user.getAuthorities());
            response.setExpirationTime("24 Hours");
            response.setMessage("Login successful");
        }
        catch(UserNotFoundException e){
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
            return response;
        } catch(Exception e) {
            response.setStatusCode(500);
            response.setMessage("Internal server error: " + e.getMessage());
            return response;
        }
        return response;
    }
    public boolean validate(String token) {
        return this.jwtService.validateToken(token);
    }

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader("Authorization");
        final String refreshToken;
        final String email;
        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        email = this.jwtService.extractUsername(refreshToken);
        if(email != null){
            var user = this.userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found"));

            //hem token içindeki expirationtime kontrol ediliyor hemde cache'te ki süresi.
            if(this.jwtService.validateToken(refreshToken) && this.tokenService.isRefreshTokenValid(email, refreshToken)) {
                //eskisin siliyoruz
                this.tokenService.invalidateAccessTokenByEmail(email);
                //yeni yaratiyoruz
                String newAccessToken = this.jwtService.generateToken(email);
                this.tokenService.saveAccessToken(email, newAccessToken);
                LoginResponse loginResponse = LoginResponse.builder()
                        .token(newAccessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), loginResponse);

            }
        }
    }

}
