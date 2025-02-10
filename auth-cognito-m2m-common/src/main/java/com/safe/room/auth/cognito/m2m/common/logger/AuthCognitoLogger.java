package com.safe.room.auth.cognito.m2m.common.logger;

public interface AuthCognitoLogger {

    void info(String message);
    void warn(String message);
    void error(String message);
    void error(String message, Throwable t);
}
