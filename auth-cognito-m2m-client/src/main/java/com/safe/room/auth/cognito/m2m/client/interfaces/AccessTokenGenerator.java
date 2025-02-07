package com.safe.room.auth.cognito.m2m.client.interfaces;

import com.safe.room.auth.cognito.m2m.client.dto.AccessToken;
import com.safe.room.auth.cognito.m2m.client.exceptions.AuthClientConfigException;
import com.safe.room.auth.cognito.m2m.client.exceptions.GenerateAccessTokenException;

/**
 * This interface represents a strategy for generating an access token using
 * AWS Cognito. Classes implementing this interface should provide a concrete
 * implementation of the {@link #generateAccessToken()} method.
 */
public interface AccessTokenGenerator {
    /**
     * Generates an access token using AWS Cognito. This method should be
     * overridden by implementing classes to provide a concrete
     * implementation of the access token generation strategy.
     *
     * @return an AccessToken instance with the current access token
     * string, the duration in seconds for which the access token is valid,
     * and the expiration time of the access token in milliseconds since
     * the epoch
     * @throws GenerateAccessTokenException if there is an error generating a
     * new access token
     */
    AccessToken generateAccessToken() throws AuthClientConfigException, GenerateAccessTokenException;
}
