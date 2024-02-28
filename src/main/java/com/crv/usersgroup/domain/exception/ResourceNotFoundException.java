package com.crv.usersgroup.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    public static final String defaultMessage = "No records found for this ID";

    public ResourceNotFoundException() {
        super(defaultMessage);
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }

}
