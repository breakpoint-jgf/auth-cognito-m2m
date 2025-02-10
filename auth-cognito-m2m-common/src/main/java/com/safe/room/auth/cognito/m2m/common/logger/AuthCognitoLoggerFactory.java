package com.safe.room.auth.cognito.m2m.common.logger;

import java.util.concurrent.atomic.AtomicReference;

public class AuthCognitoLoggerFactory {

    private static final AtomicReference<AuthCognitoLoggerFactory> instanceRef = new AtomicReference<>();
    private AuthCognitoLoggerProvider loggerProvider;

    protected AuthCognitoLoggerFactory(AuthCognitoLoggerProvider loggerProvider) {
        this.loggerProvider = loggerProvider;
    }

    private static AuthCognitoLoggerFactory getInstance() {
        AuthCognitoLoggerFactory instance = instanceRef.get();
        if (instance == null) {
            instance = new AuthCognitoLoggerFactory(defaultLoggerProvider());
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

    protected static void init(AuthCognitoLoggerProvider loggerProvider) {
        if (loggerProvider == null) return;
        AuthCognitoLoggerFactory.instanceRef.compareAndSet(AuthCognitoLoggerFactory.instanceRef.get(), new AuthCognitoLoggerFactory(loggerProvider));
    }

}
