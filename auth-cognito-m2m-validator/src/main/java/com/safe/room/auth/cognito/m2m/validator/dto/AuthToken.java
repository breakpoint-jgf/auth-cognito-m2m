package com.safe.room.auth.cognito.m2m.validator.dto;

import java.util.List;

public class AuthToken {
    private  String token;
    private  List<String> targetScopes;

    public AuthToken() {
      //required default
    }

    public AuthToken(Builder builder) {
        this.token = builder.token;
        this.targetScopes = builder.targetScopes;
    }

    public String getToken() {
        return token;
    }

    public List<String> getTargetScopes() {
        return targetScopes;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String token;
        private List<String> targetScopes;

        public Builder setToken(String token) {
            this.token = token;
            return this;
        }

        public Builder setTargetScopes(List<String> targetScopes) {
            this.targetScopes = targetScopes;
            return this;
        }

        public AuthToken build() {
            return new AuthToken(this);
        }
    }

}