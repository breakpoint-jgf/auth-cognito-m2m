package com.safe.room.tech.cognito.client.test;

import com.safe.room.tech.cognito.client.AuthCognitoClient;
import com.safe.room.tech.cognito.client.dto.AccessToken;
import com.safe.room.tech.cognito.client.dto.AuthClientConfig;
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

        AuthCognitoClient client = new AuthCognitoClient(clientConfig);
        AccessToken accessToken = client.getAccessToken();
        assertNotNull(accessToken);
        System.out.println(accessToken.getToken());
    }
}
