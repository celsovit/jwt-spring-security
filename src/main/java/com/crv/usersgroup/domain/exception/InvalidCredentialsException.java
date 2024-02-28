package com.crv.usersgroup.domain.exception;

import org.springframework.security.authentication.BadCredentialsException;

public class InvalidCredentialsException extends BadCredentialsException {

    public InvalidCredentialsException() {
        super("Invalid username/password supplied!");
    }

    public InvalidCredentialsException(String message) {
        super(message);
    }

}
