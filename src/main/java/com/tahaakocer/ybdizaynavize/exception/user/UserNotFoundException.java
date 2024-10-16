package com.tahaakocer.ybdizaynavize.exception.user;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String usernameOrEmailNotFound) {
        super(usernameOrEmailNotFound);
    }
}
