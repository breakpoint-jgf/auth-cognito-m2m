package com.safe.room.auth.cognito.m2m.client.exceptions;

public class GenerateAccessTokenException extends RuntimeException {
    public GenerateAccessTokenException(String message) {
        super(message);
    }
    public GenerateAccessTokenException(String message, Throwable t) {
        super(message, t);
    }
}
