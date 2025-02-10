package com.safe.room.auth.cognito.m2m.client.test;

import com.safe.room.auth.cognito.m2m.client.AuthCognitoClient;
import com.safe.room.auth.cognito.m2m.client.dto.AccessToken;
import com.safe.room.auth.cognito.m2m.client.dto.AuthClientConfig;
import com.safe.room.auth.cognito.m2m.common.logger.DefaultLogger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AuthCognitoClientTest {

    private static Properties props;

    @BeforeAll
    public static void setUp() throws IOException {
        props = new Properties();
        try (InputStream input = AuthCognitoClientTest.class.getResourceAsStream("/test.properties")) {
            props.load(input);
        }
    }

    @Test
    public void testAuthCognitoClient() throws Exception {

        String clientId = props.getProperty("client.id");
        String clientSecret = props.getProperty("client.secret");
        String[] scopes = props.getProperty("scopes").split(",");
        String tokenEndpoint = props.getProperty("token.endpoint");

        AuthClientConfig clientConfig = AuthClientConfig.builder()
                .setClientId(clientId)
                .setClientSecret(clientSecret)
                .setScopes(Arrays.asList(scopes))
                .setTokenEndpoint(tokenEndpoint)
                .build();

        AuthCognitoClient client = new AuthCognitoClient(clientConfig, c -> new DefaultLogger());
        AccessToken accessToken = client.getAccessToken();
        assertNotNull(accessToken);
        System.out.println(accessToken.getToken());
    }
}
