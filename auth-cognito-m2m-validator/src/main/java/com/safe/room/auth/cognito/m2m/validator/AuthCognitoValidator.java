package com.safe.room.auth.cognito.m2m.validator;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safe.room.auth.cognito.m2m.common.logger.AuthCognitoLogger;
import com.safe.room.auth.cognito.m2m.common.logger.AuthCognitoLoggerFactory;
import com.safe.room.auth.cognito.m2m.common.logger.AuthCognitoLoggerProvider;
import com.safe.room.auth.cognito.m2m.validator.dto.AuthToken;
import com.safe.room.auth.cognito.m2m.validator.dto.AuthValidatorConfig;
import com.safe.room.auth.cognito.m2m.validator.exceptions.AuthTokenValidationException;
import com.safe.room.auth.cognito.m2m.validator.exceptions.InvalidAuthTokenException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

public class AuthCognitoValidator {

    private static final AuthCognitoLogger LOGGER = AuthCognitoLoggerFactory.getLogger(AuthCognitoValidator.class);

    private AuthValidatorConfig config;

    public AuthCognitoValidator(AuthValidatorConfig config) {
        Objects.requireNonNull(config, "config cannot be null");
        this.config = config;
    }

    public AuthCognitoValidator(AuthValidatorConfig config, AuthCognitoLoggerProvider loggerProvider) {
        AuthCognitoLoggerFactoryWrapper.register(loggerProvider);
        Objects.requireNonNull(config, "config cannot be null");
        this.config = config;
        logConfig();
    }


    private void logConfig() {
        LOGGER.info("AuthCognitoValidator config: region = " + config.getRegion() +
                ", jwksUrl = " + config.getJwksUrl() +
                ", userPoolId = " + config.getUserPoolId() +
                ", issuer = " + config.getIssuer());
    }

    public void validateToken(AuthToken authToken) throws InvalidAuthTokenException {
        try {
            // Assuming `authToken.getToken()` returns the token string
            String token = authToken.getToken();

            // Fetch JWKS and get public key
            String jwksJson = fetchJWKS();
            RSAPublicKey publicKey = getPublicKeyFromJWKS(jwksJson, token);

            // Create JWT Verifier
            Algorithm algorithm = Algorithm.RSA256(publicKey, null);
            JWTVerifier verifier = JWT.require(algorithm)
                                      .withIssuer(config.getIssuer())
                                      .build();
            // Verify the token
            DecodedJWT jwt = verifier.verify(token);
            checkScope(jwt, authToken.getTargetScopes());

        } catch (com.auth0.jwt.exceptions.JWTVerificationException e) {
            LOGGER.error("Token invalid", e);
            throw new InvalidAuthTokenException("Token validation failed. Token = "+authToken.getToken(), e); // Rethrow JWT verification exceptions
        } catch (InvalidAuthTokenException e) {
            LOGGER.error("Token validation failed. Token = "+authToken.getToken(), e);
            throw e;
        }  catch (Exception e) {
            LOGGER.error("Error during Token validation", e);
            throw new AuthTokenValidationException("Error during Token validation", e);
        }

    }


    private String fetchJWKS() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(this.config.getJwksUrl())
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            return response.body().string();
        }
    }

    private static RSAPublicKey getPublicKeyFromJWKS(String jwksJson, String token) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jwks = objectMapper.readTree(jwksJson);
        String kid = JWT.decode(token).getKeyId();

        for (JsonNode key : jwks.get("keys")) {
            if (key.get("kid").asText().equals(kid)) {
                String n = key.get("n").asText(); // Modulus
                String e = key.get("e").asText(); // Exponent

                // Decode the Base64Url encoded values
                byte[] modulusBytes = Base64.getUrlDecoder().decode(n);
                byte[] exponentBytes = Base64.getUrlDecoder().decode(e);

                // Create the public key
                BigInteger modulus = new BigInteger(1, modulusBytes);
                BigInteger exponent = new BigInteger(1, exponentBytes);
                RSAPublicKeySpec spec = new RSAPublicKeySpec(modulus, exponent);
                KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                return (RSAPublicKey) keyFactory.generatePublic(spec);
            }
        }
        throw new Exception("Public key not found for the given token");
    }

    private static void checkScope(DecodedJWT jwt, List<String> requiredScopes) throws InvalidAuthTokenException {
        String scopeClaim = jwt.getClaim("scope").asString();
        if (scopeClaim == null) {
            throw new InvalidAuthTokenException("No scope claim present in the token");
        }

        // Split the scopes and check if any of the required scopes are present
        String[] scopes = scopeClaim.split(" ");
        for (String requiredScope : requiredScopes) {
            for (String scope : scopes) {
                if (scope.equals(requiredScope)) {
                    return; // Required scope found
                }
            }
        }
        throw new InvalidAuthTokenException("Required scope not present in the token");
    }


    private static class AuthCognitoLoggerFactoryWrapper extends AuthCognitoLoggerFactory {
        private AuthCognitoLoggerFactoryWrapper(AuthCognitoLoggerProvider loggerProvider) {
            super(loggerProvider);
        }
        static void register(AuthCognitoLoggerProvider loggerProvider) {
            init(loggerProvider);
        }
    }

}
