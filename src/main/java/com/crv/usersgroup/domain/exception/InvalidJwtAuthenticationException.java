package com.crv.usersgroup.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class InvalidJwtAuthenticationException extends RuntimeException {

    public InvalidJwtAuthenticationException() {
        super("Expired or invalid JWT token!");
    }

    public InvalidJwtAuthenticationException(String message) {
        super(message);
    }

}
