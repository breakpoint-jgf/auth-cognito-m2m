package com.safe.room.auth.cognito.m2m.client.impl;

import com.safe.room.auth.cognito.m2m.client.interfaces.AuthClientConfigValidator;
import com.safe.room.auth.cognito.m2m.client.dto.AuthClientConfig;
import com.safe.room.auth.cognito.m2m.client.exceptions.AuthClientConfigException;

import java.util.HashSet;
import java.util.Set;

public class AuthClientConfigValidatorImpl implements AuthClientConfigValidator {

    public void validateConfig(AuthClientConfig config) throws AuthClientConfigException {
        if (config == null) {
            throw new AuthClientConfigException("config is null");
        }
        Set<String> missingFields = new HashSet<>();
        if (config.getClientId() == null || config.getClientId().trim().isEmpty()) {
            missingFields.add("clientId");
        }
        if (config.getClientSecret() == null || config.getClientSecret().trim().isEmpty()) {
            missingFields.add("clientSecret");
        }
        if (config.getScopes() == null) {
            missingFields.add("scope");
        }
        if (config.getTokenEndpoint() == null || config.getTokenEndpoint().trim().isEmpty()) {
            missingFields.add("tokenEndpoint");
        }
        if (!missingFields.isEmpty()) {
            throw new AuthClientConfigException(
                    "The following config fields are missing: " + String.join(", ", missingFields));
        }
    }
}
