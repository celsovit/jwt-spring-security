package com.crv.usersgroup.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RequiredObjectIsNullException extends RuntimeException {

    public static final String defaultMessage = "It is not allowed to persist a null object!";

    public RequiredObjectIsNullException() {
        super(defaultMessage);
    }

    public RequiredObjectIsNullException(String message) {
        super(message);
    }

}