package com.tahaakocer.ybdizaynavize.service.user;

import com.tahaakocer.ybdizaynavize.dto.user.UserDto;
import com.tahaakocer.ybdizaynavize.dto.user.response.LoginResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface IUserService {
    UserDto register(UserDto userDto);
    LoginResponse login(UserDto userDto);
    boolean validate(String token);
    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
