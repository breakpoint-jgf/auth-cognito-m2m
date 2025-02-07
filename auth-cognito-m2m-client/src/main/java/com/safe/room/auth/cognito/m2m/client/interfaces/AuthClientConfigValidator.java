package com.safe.room.auth.cognito.m2m.client.interfaces;

import com.safe.room.auth.cognito.m2m.client.dto.AuthClientConfig;
import com.safe.room.auth.cognito.m2m.client.exceptions.AuthClientConfigException;

/**
 * This interface defines a contract for validating the configuration
 * required for authenticating with an OAuth2.0 client using AWS Cognito.
 * Implementing classes should provide a concrete implementation of the
 * {@link #validateConfig(AuthClientConfig)} method.
 */
public interface AuthClientConfigValidator {
    /**
     * Validates the provided AuthClientConfig.
     *
     * @param config the configuration to validate
     * @throws AuthClientConfigException if the configuration is invalid
     */
    void validateConfig(AuthClientConfig config) throws AuthClientConfigException;
}
