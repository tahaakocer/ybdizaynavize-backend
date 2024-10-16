package com.tahaakocer.ybdizaynavize.security;

import com.tahaakocer.ybdizaynavize.exception.user.TokenInvalidException;
import io.jsonwebtoken.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
@Log4j2
public class JwtService {
    @Value("${jwt.key}")
    private String SECRET;
    private static final long EXPIRATION_REFRESH_TIME = 1000 * 60 * 60 * 24; //for 1 day
    private static final long EXPIRATION_ACCESS_TIME = 1000 * 60 * 15; //for 15 minutes
    private final TokenService tokenService;

    public JwtService(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    public String generateToken(String email) {
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + EXPIRATION_ACCESS_TIME))
                .signWith(getSignKey())
                .compact();
    }
    public String generateRefreshToken(String email) {
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + EXPIRATION_REFRESH_TIME))
                .signWith(getSignKey())
                .compact();
    }

    public boolean validateToken(String token) {

        try {
            var parsedToken = Jwts.parser()
                    .verifyWith(getSignKey())
                    .build()
                    .parseSignedClaims(token);
            String email = parsedToken.getPayload().getSubject();
            if(!this.tokenService.isAccessTokenValid(email, token) && !this.tokenService.isRefreshTokenValid(email, token)) {
                throw new TokenInvalidException("Invalid JWT token");
            }
            return true;
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }

    private Date extractExpiration(String token) {
        Claims claims =  Jwts.parser().verifyWith(getSignKey()).build().parseSignedClaims(token).getPayload();
        return claims.getExpiration();
    }
    public String extractUsername(String token) {
        Claims claims = Jwts.parser().verifyWith(getSignKey()).build().parseSignedClaims(token).getPayload();
        return claims.getSubject();
    }
    private SecretKey getSignKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET));

    }
}
