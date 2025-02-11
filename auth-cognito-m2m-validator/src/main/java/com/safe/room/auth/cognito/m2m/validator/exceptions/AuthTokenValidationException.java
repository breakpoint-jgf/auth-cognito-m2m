package com.safe.room.auth.cognito.m2m.validator.exceptions;

public class AuthTokenValidationException extends RuntimeException {
    public AuthTokenValidationException(String message, Throwable t) {
        super(message, t);
    }
}
