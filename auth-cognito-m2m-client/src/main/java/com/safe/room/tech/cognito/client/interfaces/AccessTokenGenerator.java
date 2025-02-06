package com.safe.room.tech.cognito.client.interfaces;

import com.safe.room.tech.cognito.client.dto.AccessToken;
import com.safe.room.tech.cognito.client.exceptions.GenerateAccessTokenException;

public interface AccessTokenGenerator {
    AccessToken generateAccessToken() throws GenerateAccessTokenException;
}
