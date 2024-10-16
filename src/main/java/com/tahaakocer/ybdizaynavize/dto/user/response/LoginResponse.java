package com.tahaakocer.ybdizaynavize.dto.user.response;

import com.tahaakocer.ybdizaynavize.model.user.Role;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponse {
    private int statusCode;
    private String message;
    private UUID id;
    private String token;
    private String refreshToken;
    private Set<Role> authorities;
    private String expirationTime;

}