package com.safe.room.auth.cognito.m2m.validator.dto;

public class AuthValidatorConfig {

    private String region;
    private String jwksUrl;
    private String userPoolId;
    private String issuer;

    public AuthValidatorConfig(){
        //required default
    }

    private AuthValidatorConfig(Builder builder) {
        this.region = builder.region;
        this.jwksUrl = builder.jwksUrl;
        this.userPoolId = builder.userPoolId;
        this.issuer = builder.issuer;
    }

    public String getRegion() {
        return region;
    }

    public String getJwksUrl() {
        return jwksUrl;
    }

    public String getUserPoolId() {
        return userPoolId;
    }

    public String getIssuer() {
        return issuer;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String region;
        private String jwksUrl;
        private String userPoolId;
        private String issuer;

        public Builder setRegion(String region) {
            this.region = region;
            return this;
        }

        public Builder setJwksUrl(String jwksUrl) {
            this.jwksUrl = jwksUrl;
            return this;
        }

        public Builder setUserPoolId(String userPoolId) {
            this.userPoolId = userPoolId;
            return this;
        }

        public Builder setIssuer(String issuer) {
            this.issuer = issuer;
            return this;
        }

        public AuthValidatorConfig build() {
            return new AuthValidatorConfig(this);
        }
    }

}
