package com.safe.room.auth.cognito.m2m.common.logger;

public class DefaultLogger implements AuthCognitoLogger {

    @Override
    public void info(String message) {
        System.out.println("INFO: " + message);
    }

    @Override
    public void warn(String message) {
        System.out.println("WARN: " + message);
    }

    @Override
    public void error(String message) {
        System.out.println("ERROR: " + message);
    }

    @Override
    public void info(String message, Object... args) {
        System.out.println("INFO: " + String.format(message, args));
    }

    @Override
    public void warn(String message, Object... args) {
        System.out.println("WARN: " + String.format(message, args));
    }

    @Override
    public void error(String message, Object... args) {
        System.out.println("ERROR: " + String.format(message, args));
    }
}
