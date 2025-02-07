package com.safe.room.auth.cognito.m2m.client;

import com.safe.room.auth.cognito.m2m.client.dto.AuthClientConfig;
import com.safe.room.auth.cognito.m2m.client.impl.AccessTokenGeneratorImpl;
import com.safe.room.auth.cognito.m2m.client.impl.AccessTokenManagerImpl;
import com.safe.room.auth.cognito.m2m.client.impl.AuthClientConfigValidatorImpl;
import com.safe.room.auth.cognito.m2m.client.interfaces.AccessTokenGenerator;
import com.safe.room.auth.cognito.m2m.client.interfaces.AccessTokenManager;
import com.safe.room.auth.cognito.m2m.client.interfaces.AuthClientConfigValidator;
/**
 * A factory class for creating AccessTokenManager instances based on the
 * provided AuthClientConfig.
 */
public class AccessTokenManagerFactory {

    /**
     * Creates an instance of AccessTokenManager using the provided AuthClientConfig.
     *
     * @param authClientConfig the configuration required for authenticating with AWS Cognito
     * @return an AccessTokenManager instance
     */
    public static AccessTokenManager create(AuthClientConfig authClientConfig) {
        AuthClientConfigValidator validator = new AuthClientConfigValidatorImpl();
        AccessTokenGenerator generator = new AccessTokenGeneratorImpl(validator, authClientConfig);
        return new AccessTokenManagerImpl(generator);
    }

}
