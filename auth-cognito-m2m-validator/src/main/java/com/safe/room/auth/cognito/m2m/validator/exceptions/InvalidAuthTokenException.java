package com.safe.room.auth.cognito.m2m.validator.exceptions;

public class InvalidAuthTokenException extends Exception {
    public InvalidAuthTokenException(String message) {
        super(message);
    }
    public InvalidAuthTokenException(String message, Throwable t) {
        super(message, t);
    }
}
