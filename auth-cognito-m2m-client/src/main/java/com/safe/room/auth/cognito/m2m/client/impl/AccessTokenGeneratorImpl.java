package com.safe.room.auth.cognito.m2m.client.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safe.room.auth.cognito.m2m.client.interfaces.AccessTokenGenerator;
import com.safe.room.auth.cognito.m2m.client.dto.AccessToken;
import com.safe.room.auth.cognito.m2m.client.dto.AuthClientConfig;
import com.safe.room.auth.cognito.m2m.client.exceptions.GenerateAccessTokenException;
import com.safe.room.auth.cognito.m2m.client.interfaces.AuthClientConfigValidator;
import com.safe.room.auth.cognito.m2m.common.logger.AuthCognitoLogger;
import com.safe.room.auth.cognito.m2m.common.logger.AuthCognitoLoggerFactory;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class AccessTokenGeneratorImpl implements AccessTokenGenerator {

    private static final AuthCognitoLogger LOGGER = AuthCognitoLoggerFactory.getLogger(AccessTokenGeneratorImpl.class);

    private final AuthClientConfig authClientConfig;
    private final OkHttpClient client;
    private final ObjectMapper objectMapper;

    public AccessTokenGeneratorImpl(AuthClientConfigValidator authClientConfigValidator, AuthClientConfig authClientConfig) {
        authClientConfigValidator.validateConfig(authClientConfig);
        this.authClientConfig = authClientConfig;
        this.client = new OkHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    public AccessToken generateAccessToken() throws GenerateAccessTokenException {

        try {

            final long startTime = System.currentTimeMillis();
            Request request = createRequest();
            LOGGER.info("Requesting token from : " + this.authClientConfig.getTokenEndpoint());
            Response response = this.client.newCall(request).execute();
            inspectResponse(response);
            JsonNode jsonResponse = this.objectMapper.readTree(response.body().string());

            // Store the access token and expiration time
            String token = jsonResponse.get("access_token").asText();
            long expiresIn = jsonResponse.get("expires_in").asLong();
            long expirationTime = startTime + TimeUnit.SECONDS.toMillis(expiresIn);

            return AccessToken.builder()
                    .setToken(token)
                    .setExpiresIn(expiresIn)
                    .setExpirationTime(expirationTime)
                    .build();

        } catch (GenerateAccessTokenException e){
            throw e;
        } catch (Exception e){
            throw new GenerateAccessTokenException("Error generating access token: " + e.getMessage(), e);
        }

    }

    private void inspectResponse(Response response) throws GenerateAccessTokenException, IOException {
        if (!response.isSuccessful()) {
            String errorMsg = String.format("Request failed with status code = %s, body = %s",
                    response.code(), Objects.nonNull(response.body()) ? response.body().string() : null);
            LOGGER.error(errorMsg);
            throw new GenerateAccessTokenException(errorMsg);
        }
    }

    private String resolveScope(Collection<String> scopes) {
        if (scopes == null || scopes.isEmpty()) {
            return null;
        }
        return String.join(" ", new HashSet<>(scopes));
    }

    private Request createRequest() {

        String clientId = this.authClientConfig.getClientId();
        String clientSecret = this.authClientConfig.getClientSecret();
        String scope = resolveScope(this.authClientConfig.getScopes());
        String tokenEndpoint = this.authClientConfig.getTokenEndpoint();

        FormBody.Builder requestBodyBuilder = new FormBody.Builder()
                .add("grant_type", "client_credentials")
                .add("client_id", clientId)
                .add("client_secret", clientSecret);

        if (scope != null) {
            requestBodyBuilder.add("scope", scope);
        }

        FormBody requestBody = requestBodyBuilder.build();

        return new Request.Builder()
                .url(tokenEndpoint)
                .post(requestBody)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .build();
    }


}
