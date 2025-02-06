package com.safe.room.tech.cognito.client.impl;

import com.safe.room.tech.cognito.client.interfaces.AccessTokenGenerator;
import com.safe.room.tech.cognito.client.interfaces.AccessTokenManager;
import com.safe.room.tech.cognito.client.dto.AccessToken;
import com.safe.room.tech.cognito.client.exceptions.GenerateAccessTokenException;
import com.safe.room.tech.cognito.client.exceptions.AuthClientConfigException;

import java.time.Instant;
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

        AccessToken accessToken = accessTokenRef.get();

        if (accessTokenRef.get() == null){
            accessTokenRef.set(accessTokenGenerator.generateAccessToken());
            return accessTokenRef.get();
        }

        if (isTokenExpired(accessTokenRef.get())) {
            AccessToken newAccessToken = accessTokenGenerator.generateAccessToken();
            if (accessTokenRef.compareAndSet(accessTokenRef.get(), newAccessToken)) {
                return newAccessToken;
            }
            // another thread generated the token, return the latest one
            return accessTokenRef.get();
        }

        // token is valid, no need to update
        return accessTokenRef.get();
    }

    private boolean isTokenExpired(AccessToken accessToken) {
        long expirationTime = accessToken.getExpirationTime();
        long currentTime = Instant.now().toEpochMilli();
        return currentTime + TOKEN_REFRESH_THRESHOLD > expirationTime;
    }
}
