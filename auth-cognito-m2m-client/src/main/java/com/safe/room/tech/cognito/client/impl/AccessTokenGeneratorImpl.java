package com.safe.room.tech.cognito.client.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safe.room.tech.cognito.client.interfaces.AccessTokenGenerator;
import com.safe.room.tech.cognito.client.dto.AccessToken;
import com.safe.room.tech.cognito.client.dto.AuthClientConfig;
import com.safe.room.tech.cognito.client.exceptions.GenerateAccessTokenException;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;

public class AccessTokenGeneratorImpl implements AccessTokenGenerator {

    private final AuthClientConfig authClientConfig;
    private final OkHttpClient client;
    private final ObjectMapper objectMapper;

    public AccessTokenGeneratorImpl(AuthClientConfig authClientConfig) {
        this.authClientConfig = authClientConfig;
        this.client = new OkHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    public AccessToken generateAccessToken() throws GenerateAccessTokenException {

        try {

            final long startTime = System.currentTimeMillis();
            Request request = createRequest();
            Response response = this.client.newCall(request).execute();

            if (!response.isSuccessful()) {
                throw new IOException("Request failed with status code = " + response.code() + ", body = " + response.body().string());
            }

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

        } catch (Exception e){
            throw new GenerateAccessTokenException("Error generating access token: " + e.getMessage(), e);
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
