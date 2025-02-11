package com.safe.room.auth.cognito.m2m.validator.test;

import com.safe.room.auth.cognito.m2m.common.logger.DefaultLogger;
import com.safe.room.auth.cognito.m2m.validator.AuthCognitoValidator;
import com.safe.room.auth.cognito.m2m.validator.dto.AuthToken;
import com.safe.room.auth.cognito.m2m.validator.dto.AuthValidatorConfig;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;

public class AuthCognitoValidatorTest {

    private static Properties props;

    @BeforeAll
    public static void setUp() throws IOException {
        props = new Properties();
        try (InputStream input = AuthCognitoValidatorTest.class.getResourceAsStream("/test.properties")) {
            props.load(input);
        }
    }


    private String authToken(){
        return "replace with your token";
    }

    @Test
    void testAuthCognitoClient() throws Exception {

        String region = props.getProperty("region");
        String userPoolId = props.getProperty("user.pool.id");
        String jwksUrl = props.getProperty("jwks.url");
        String issuer = props.getProperty("issuer");
        String[] scopes = props.getProperty("scopes").split(",");

        AuthValidatorConfig config = AuthValidatorConfig.builder()
                .setRegion(region)
                .setUserPoolId(userPoolId)
                .setJwksUrl(jwksUrl)
                .setIssuer(issuer)
                .build();

        String token = authToken();

        AuthCognitoValidator authCognitoValidator = new AuthCognitoValidator(config, c -> new DefaultLogger());
        authCognitoValidator.validateToken(AuthToken.builder()
                .setToken(token)
                .setTargetScopes(Arrays.asList(scopes))
                .build());

    }
}
