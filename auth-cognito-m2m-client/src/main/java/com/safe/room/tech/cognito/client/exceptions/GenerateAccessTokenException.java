package com.safe.room.tech.cognito.client.exceptions;

public class GenerateAccessTokenException extends RuntimeException {
    public GenerateAccessTokenException(String message) {
        super(message);
    }
    public GenerateAccessTokenException(String message, Throwable t) {
        super(message, t);
    }
}
