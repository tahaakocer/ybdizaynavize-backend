package com.tahaakocer.ybdizaynavize.exception.user;

public class TokenInvalidException extends RuntimeException {
    public TokenInvalidException(String invalidJwtToken) {
        super(invalidJwtToken);
    }
}
