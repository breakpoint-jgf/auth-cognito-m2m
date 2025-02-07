package com.safe.room.auth.cognito.m2m.client.impl;

import com.safe.room.auth.cognito.m2m.client.interfaces.AccessTokenGenerator;
import com.safe.room.auth.cognito.m2m.client.interfaces.AccessTokenManager;
import com.safe.room.auth.cognito.m2m.client.dto.AccessToken;
import com.safe.room.auth.cognito.m2m.client.exceptions.GenerateAccessTokenException;
import com.safe.room.auth.cognito.m2m.client.exceptions.AuthClientConfigException;

import java.time.Instant;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class AccessTokenManagerImpl implements AccessTokenManager {

    private static final long TOKEN_REFRESH_THRESHOLD = TimeUnit.MINUTES.toMillis(5);

    private final AtomicReference<AccessToken> accessTokenRef;
    private final AccessTokenGenerator accessTokenGenerator;

    public AccessTokenManagerImpl(AccessTokenGenerator accessTokenGenerator) throws AuthClientConfigException {
        this.accessTokenGenerator = accessTokenGenerator;
        this.accessTokenRef = new AtomicReference<>();
    }


    public AccessToken getAccessToken() throws GenerateAccessTokenException {

        // This code attempts to return an AccessToken instance that is
        // currently valid. If the current access token is not available or
        // has expired, it will generate a new one using the provided
        // AccessTokenGenerator. The new access token is published to the
        // other threads only if the current access token has changed. This
        // ensures that all threads will eventually see the new access token
        // and use it to authenticate with AWS Cognito.

        Optional<AccessToken> currentToken = Optional.ofNullable(accessTokenRef.get());

        if (!currentToken.isPresent() || isTokenExpired(currentToken.get())) {
            AccessToken newAccessToken = accessTokenGenerator.generateAccessToken();
            while (!accessTokenRef.compareAndSet(currentToken.orElse(null), newAccessToken)) {
                currentToken = Optional.ofNullable(accessTokenRef.get());
                if (currentToken.isPresent() && !isTokenExpired(currentToken.get())) {
                    return currentToken.get();
                }
            }
        }

        return accessTokenRef.get();
    }

    private boolean isTokenExpired(AccessToken accessToken) {
        // Checks if the access token has expired by comparing the current
        // time against the expiration time. The token is considered expired
        // if the current time is greater than the expiration time minus a
        // threshold of 5 minutes. This ensures that the access token is
        // refreshed before it actually expires.
        long expirationTime = accessToken.getExpirationTime();
        long currentTime = Instant.now().toEpochMilli();
        return currentTime + TOKEN_REFRESH_THRESHOLD > expirationTime;
    }
}
