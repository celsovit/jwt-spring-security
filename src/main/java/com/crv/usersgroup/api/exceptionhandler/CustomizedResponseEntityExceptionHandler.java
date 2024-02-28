package com.crv.usersgroup.api.exceptionhandler;


import com.crv.usersgroup.domain.exception.InvalidCredentialsException;
import com.crv.usersgroup.domain.exception.InvalidJwtAuthenticationException;
import com.crv.usersgroup.domain.exception.RequiredObjectIsNullException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.naming.AuthenticationException;
import java.util.Date;

@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    public final ResponseEntity<ExceptionResponse> handleAccessDeniedException(Exception ex, WebRequest request) {
        return getExceptionResponseEntity(ex, request, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public final ResponseEntity<ExceptionResponse> handleInvalidCredentialsExceptions(Exception ex, WebRequest request) {
        return getExceptionResponseEntity(ex, request, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(RequiredObjectIsNullException.class)
    public final ResponseEntity<ExceptionResponse> handleBadRequestExceptions(Exception ex, WebRequest request) {
        return getExceptionResponseEntity(ex, request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidJwtAuthenticationException.class)
    public final ResponseEntity<ExceptionResponse>
    handleInvalidJwtAuthenticationExceptions(Exception ex, WebRequest request) {
        return getExceptionResponseEntity(ex, request, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionResponse> handleAllExceptions(Exception ex, WebRequest request) {
        return getExceptionResponseEntity(ex, request, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private static ResponseEntity<ExceptionResponse> getExceptionResponseEntity(
            Exception ex, WebRequest request, HttpStatus internalServerError) {

        ExceptionResponse exceptionResponse = new ExceptionResponse(
                new Date(),
                ex.getMessage(),
                request.getDescription(false)
        );

        return new ResponseEntity<>(exceptionResponse, internalServerError);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ExceptionResponse> handleAuthenticationException(Exception ex, WebRequest request) {
        return getExceptionResponseEntity(ex, request, HttpStatus.FORBIDDEN);
    }

}
