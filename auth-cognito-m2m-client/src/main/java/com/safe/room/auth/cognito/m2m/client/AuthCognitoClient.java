package com.safe.room.auth.cognito.m2m.client;

import com.safe.room.auth.cognito.m2m.client.dto.AccessToken;
import com.safe.room.auth.cognito.m2m.client.dto.AuthClientConfig;
import com.safe.room.auth.cognito.m2m.client.exceptions.GenerateAccessTokenException;
import com.safe.room.auth.cognito.m2m.client.exceptions.AuthClientConfigException;
import com.safe.room.auth.cognito.m2m.client.interfaces.AccessTokenManager;
import com.safe.room.auth.cognito.m2m.common.logger.AuthCognitoLoggerProvider;
import com.safe.room.auth.cognito.m2m.common.logger.AuthCognitoLoggerFactory;

/**
 * This class represents a client for interacting with AWS Cognito using the
 * client credentials flow. It provides a convenient interface for obtaining an
 * access token for a given client configuration.
 *
 */
public class AuthCognitoClient {

    private final AccessTokenManager accessTokenManager;

    /**
     * Returns an instance of the AuthCognitoClient class configured with the
     * provided client configuration. The constructor validates the provided
     * configuration and throws an AuthClientConfigException if it is invalid.
     *
     * @param authClientConfig the client configuration to use for authentication
     * @throws AuthClientConfigException if the provided configuration is invalid
     */
    public AuthCognitoClient(AuthClientConfig authClientConfig) throws AuthClientConfigException {
        accessTokenManager = AccessTokenManagerFactory.create(authClientConfig);
    }

    /**
     * Constructs an instance of AuthCognitoClient using the provided AuthClientConfig
     * and AuthCognitoLoggerProvider. Initializes the logger factory and creates
     * an AccessTokenManager with the given configuration.
     *
     * @param authClientConfig the client configuration for authentication
     * @param loggerProvider the logger provider to initialize the logger factory
     * @throws AuthClientConfigException if the provided configuration is invalid
     */
    public AuthCognitoClient(AuthClientConfig authClientConfig, AuthCognitoLoggerProvider loggerProvider) throws AuthClientConfigException {
        AuthCognitoLoggerFactoryWrapper.register(loggerProvider);
        accessTokenManager = AccessTokenManagerFactory.create(authClientConfig);
    }

    /**
     * Returns an AccessToken instance with the current access token
     * string, the duration in seconds for which the access token is valid,
     * and the expiration time of the access token in milliseconds since the epoch.
     *
     * @return an AccessToken instance with the current access token
     * @throws GenerateAccessTokenException if there is an error generating a new access token
     */
    public AccessToken getAccessToken() throws GenerateAccessTokenException {
        return accessTokenManager.getAccessToken();
    }

    /**
     * Returns the current access token string.
     *
     * @return the current access token string
     * @throws GenerateAccessTokenException if there is an error generating a new access token
     */
    public String getAccessTokenString() throws GenerateAccessTokenException {
        return getAccessToken().getToken();
    }

    private static class AuthCognitoLoggerFactoryWrapper extends AuthCognitoLoggerFactory {
        private AuthCognitoLoggerFactoryWrapper(AuthCognitoLoggerProvider loggerProvider) {
            super(loggerProvider);
        }
        static void register(AuthCognitoLoggerProvider loggerProvider) {
            init(loggerProvider);
        }
    }

}