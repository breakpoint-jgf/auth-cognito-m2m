package com.safe.room.tech.cognito.client.dto;

import java.util.Collection;

public class AuthClientConfig {

    private final String clientId;
    private final String clientSecret;
    private final Collection<String> scopes;
    private final String tokenEndpoint;

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public Collection<String> getScopes() {
        return scopes;
    }

    public String getTokenEndpoint() {
        return tokenEndpoint;
    }

    private AuthClientConfig(Builder builder) {
        this.clientId = builder.clientId;
        this.clientSecret = builder.clientSecret;
        this.scopes = builder.scopes;
        this.tokenEndpoint = builder.tokenEndpoint;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String clientId;
        private String clientSecret;
        private Collection<String> scopes;
        private String tokenEndpoint;

        public Builder setClientId(String clientId) {
            this.clientId = clientId;
            return this;
        }

        public Builder setClientSecret(String clientSecret) {
            this.clientSecret = clientSecret;
            return this;
        }

        public Builder setScopes(Collection<String> scopes) {
            this.scopes = scopes;
            return this;
        }

        public Builder setTokenEndpoint(String tokenEndpoint) {
            this.tokenEndpoint = tokenEndpoint;
            return this;
        }

        public AuthClientConfig build() {
            return new AuthClientConfig(this);
        }
    }

}
