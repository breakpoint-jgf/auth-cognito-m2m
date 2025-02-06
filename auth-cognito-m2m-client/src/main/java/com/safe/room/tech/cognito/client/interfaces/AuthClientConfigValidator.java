package com.safe.room.tech.cognito.client.interfaces;

import com.safe.room.tech.cognito.client.dto.AuthClientConfig;
import com.safe.room.tech.cognito.client.exceptions.AuthClientConfigException;

public interface AuthClientConfigValidator {
    void validateConfig(AuthClientConfig config) throws AuthClientConfigException;
}
