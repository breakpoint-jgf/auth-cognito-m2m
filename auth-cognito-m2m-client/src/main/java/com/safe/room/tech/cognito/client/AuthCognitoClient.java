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


public class AuthCognitoClient {

    private final AuthClientConfigValidator authClientConfigValidator;
    private final AccessTokenGenerator accessTokenGenerator;
    private final AccessTokenManager accessTokenManager;

    public AuthCognitoClient(AuthClientConfig authClientConfig) throws AuthClientConfigException {
        authClientConfigValidator = new AuthClientConfigValidatorImpl();
        authClientConfigValidator.validateConfig(authClientConfig);
        accessTokenGenerator = new AccessTokenGeneratorImpl(authClientConfig);
        accessTokenManager = new AccessTokenManagerImpl(accessTokenGenerator);
    }

    public AccessToken getAccessToken() throws GenerateAccessTokenException {
        return accessTokenManager.getAccessToken();
    }

    public String getAccessTokenString() throws GenerateAccessTokenException {
        return getAccessToken().getToken();
    }

}