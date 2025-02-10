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
    public void error(String message, Throwable t) {
        System.err.println("ERROR: " + message + ", STACKTRACE: ");
        t.printStackTrace(System.err);
    }
}
