package com.safe.room.tech.cognito.client.dto;

import java.io.Serializable;

public class AccessToken implements Serializable {

    private String token;
    private long expiresIn;
    private long expirationTime;

    public AccessToken(){
    }

    public AccessToken(Builder builder) {
        this.token = builder.token;
        this.expiresIn = builder.expiresIn;
        this.expirationTime = builder.expirationTime;
    }

    /**
     * Gets the access token string.
     *
     * @return the access token string
     */
    public String getToken() {
        return token;
    }

    /**
     * Gets the duration in seconds for which the access token is valid.
     *
     * @return the duration in seconds for which the access token is valid
     */
    public long getExpiresIn() {
        return expiresIn;
    }

    /**
     * Gets the expiration time of the access token in milliseconds since the epoch.
     *
     * @return the expiration time of the access token in milliseconds since the epoch
     */
    public long getExpirationTime() {
        return expirationTime;
    }


    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String token;
        private long expiresIn;
        private long expirationTime;

        /**
         * Sets the access token string.
         *
         * @param token the access token string
         * @return the builder instance
         */
        public Builder setToken(String token) {
            this.token = token;
            return this;
        }

        /**
         * Sets the duration in seconds for which the access token is valid.
         *
         * @param expiresIn the duration in seconds for which the access token is valid
         * @return the builder instance
         */
        public Builder setExpiresIn(long expiresIn) {
            this.expiresIn = expiresIn;
            return this;
        }

        /**
         * Sets the expiration time of the access token in milliseconds since the epoch.
         *
         * @param expirationTime the expiration time of the access token in milliseconds since the epoch
         * @return the builder instance
         */
        public Builder setExpirationTime(long expirationTime) {
            this.expirationTime = expirationTime;
            return this;
        }

        /**
         * Builds an AccessToken instance using the configured properties.
         *
         * @return a new AccessToken instance
         */
        public AccessToken build() {
            return new AccessToken(this);
        }
    }
}
