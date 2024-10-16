package com.tahaakocer.ybdizaynavize.security;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class TokenService {
    private final RedisTemplate<String, Object> redisTemplate;

    public TokenService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void saveAccessToken(String email, String accessToken) {
        redisTemplate.opsForHash().put(email, "accessToken", accessToken);
        redisTemplate.expire(email, Duration.ofMinutes(15));  // Access token süresi 60 dakika
    }

    public void saveRefreshToken(String email, String refreshToken) {
        redisTemplate.opsForHash().put(email, "refreshToken", refreshToken);
        redisTemplate.expire(email, Duration.ofDays(1));  // Refresh token süresi 24 saat
    }

    public String getAccessTokenByEmail(String email) {
        return (String) redisTemplate.opsForHash().get(email, "accessToken");
    }

    public String getRefreshTokenByEmail(String email) {
        return (String) redisTemplate.opsForHash().get(email, "refreshToken");
    }

    public boolean isRefreshTokenValid(String email, String refreshToken) {
        String storedRefreshToken = (String) redisTemplate.opsForHash().get(email, "refreshToken");
        return refreshToken.equals(storedRefreshToken);
    }
    public boolean isAccessTokenValid(String email, String accessToken) {
        String storedAccessToken = (String) redisTemplate.opsForHash().get(email, "accessToken");
        return accessToken.equals(storedAccessToken);
    }

    public void invalidateTokensByEmail(String email) {
        redisTemplate.delete(email);  // Email ile ilgili tüm token'ları siliyoruz (hem access hem refresh)
    }

    public void invalidateAccessTokenByEmail(String email) {
        redisTemplate.opsForHash().delete(email, "accessToken");  // Sadece access token siliniyor
    }

    public void invalidateRefreshTokenByEmail(String email) {
        redisTemplate.opsForHash().delete(email, "refreshToken");  // Sadece refresh token siliniyor
    }
}