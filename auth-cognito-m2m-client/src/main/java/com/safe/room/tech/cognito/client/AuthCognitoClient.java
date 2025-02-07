package com.safe.room.tech.cognito.client;

import com.safe.room.tech.cognito.client.dto.AccessToken;
import com.safe.room.tech.cognito.client.dto.AuthClientConfig;
import com.safe.room.tech.cognito.client.exceptions.GenerateAccessTokenException;
import com.safe.room.tech.cognito.client.exceptions.AuthClientConfigException;
import com.safe.room.tech.cognito.client.impl.AccessTokenGeneratorImpl;
import com.safe.room.tech.cognito.client.impl.AccessTokenManagerImpl;
import com.safe.room.tech.cognito.client.impl.AuthClientConfigValidatorImpl;
import com.safe.room.tech.cognito.client.interfaces.AccessTokenGenerator;
import com.safe.room.tech.cognito.client.interfaces.AccessTokenManager;
import com.safe.room.tech.cognito.client.interfaces.AuthClientConfigValidator;

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

}