package com.safe.room.auth.cognito.m2m.common.logger;

public interface AuthCognitoLogger {

    void info(String message);
    void warn(String message);
    void error(String message);

    default void info(String message, Object ... args) {
        info(String.format(message, args));
    }
    default void warn(String message, Object ... args) {
        warn(String.format(message, args));
    }

    default void error(String message, Object ... args) {
        error(String.format(message, args));
    }

}
