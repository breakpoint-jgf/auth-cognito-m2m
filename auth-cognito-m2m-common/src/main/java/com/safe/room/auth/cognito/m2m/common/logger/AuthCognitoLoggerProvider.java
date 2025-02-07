package com.safe.room.auth.cognito.m2m.common.logger;

@FunctionalInterface
public interface AuthCognitoLoggerProvider {
    AuthCognitoLogger getLogger(Class<?> clazz);
}
