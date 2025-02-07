package com.safe.room.tech.cognito.client.interfaces;

import com.safe.room.tech.cognito.client.dto.AccessToken;
import com.safe.room.tech.cognito.client.exceptions.GenerateAccessTokenException;
/**
 * This interface represents a strategy for managing access tokens using
 * AWS Cognito. Classes implementing this interface should provide a concrete
 * implementation of the {@link #getAccessToken()} method.
 */
public interface AccessTokenManager {
    /**
     * Retrieves the current access token, generating a new one if necessary.
     *
     * @return the current AccessToken
     * @throws GenerateAccessTokenException if there is an error generating a new access token
     */
    AccessToken getAccessToken() throws GenerateAccessTokenException;
}
