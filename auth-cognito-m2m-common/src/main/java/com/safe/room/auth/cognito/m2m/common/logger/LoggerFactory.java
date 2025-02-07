package com.safe.room.auth.cognito.m2m.common.logger;

import java.util.concurrent.atomic.AtomicReference;

public class LoggerFactory {

    private static final AtomicReference<LoggerFactory> instanceRef = new AtomicReference<>();
    private AuthCognitoLoggerProvider loggerProvider;

    private LoggerFactory(AuthCognitoLoggerProvider loggerProvider) {
        this.loggerProvider = loggerProvider;
    }

    private static LoggerFactory getInstance() {
        LoggerFactory instance = instanceRef.get();
        if (instance == null) {
            instance = new LoggerFactory(defaultLoggerProvider());
            if (!instanceRef.compareAndSet(null, instance)) {
                instance = instanceRef.get();
            }
        }
        return instance;
    }

    private static AuthCognitoLoggerProvider defaultLoggerProvider() {
        return clazz1 -> new DefaultLogger();
    }

    public static AuthCognitoLogger getLogger(Class<?> clazz) {
        return getInstance().loggerProvider.getLogger(clazz);
    }

    public static void init(AuthCognitoLoggerProvider loggerProvider) {
        if (loggerProvider == null) return;
        LoggerFactory.instanceRef.compareAndSet(LoggerFactory.instanceRef.get(), new LoggerFactory(loggerProvider));
    }

}
