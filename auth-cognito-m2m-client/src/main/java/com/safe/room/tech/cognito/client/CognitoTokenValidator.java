package com.safe.room.tech.cognito.client;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

public class CognitoTokenValidator {

    // Replace with your values
    private static final String REGION = "ap-southeast-1"; // e.g., us-east-1
    private static final String JWKS_URL = "https://cognito-idp."+REGION+".amazonaws.com/{userPoolId}/.well-known/jwks.json";
    private static final String USER_POOL_ID = "ap-southeast-1_P6egb1s2b"; // Your Cognito User Pool ID

    public static void main(String[] args) {
        String token = "eyJraWQiOiIrRmpmbTJ6R2Q3YnlLU2kybk9cL0tZSVl0MGZuVFRGSzB1d0xKVzJ2dWVSaz0iLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiI1aHJraXAwNGh2NjdzbXJxNm80MWpja2JiNiIsInRva2VuX3VzZSI6ImFjY2VzcyIsInNjb3BlIjoiZGVmYXVsdC1tMm0tcmVzb3VyY2Utc2VydmVyLWN2Z3FkcFwvcmVhZCIsImF1dGhfdGltZSI6MTczODgwODg4MCwiaXNzIjoiaHR0cHM6XC9cL2NvZ25pdG8taWRwLmFwLXNvdXRoZWFzdC0xLmFtYXpvbmF3cy5jb21cL2FwLXNvdXRoZWFzdC0xX1A2ZWdiMXMyYiIsImV4cCI6MTczODgxMjQ4MCwiaWF0IjoxNzM4ODA4ODgwLCJ2ZXJzaW9uIjoyLCJqdGkiOiI3NGVjYzMwNy1lNWJkLTRlYjQtYjk4Mi0yMjk3NDQyN2VjNGMiLCJjbGllbnRfaWQiOiI1aHJraXAwNGh2NjdzbXJxNm80MWpja2JiNiJ9.VveoDS1MeNlMsftgHltdZw81h1AOfb1Lwqh-CbBYvGn4N4IJuKZ5XqJBKGCrg2zDYPtYCe1RFLg2bBjVZKCyQiVA58ZnVr7kNjGCw8JzP4vqr5hnizoaN66tk0D7C3CMKiaSORlD2JQdaE-DxQAgI4DnaXxGrxAow6a-K_l2NPnR_Ku8GKXGqBhhAL4N2rQBTF6UQy_pdU5y0qyKbxKywmWVVb7E4O8yREkumRUi4X852ETfoRLSeuFqmb1Y25jCigoGIwfkjaeJXs-0VLqX82BWP40b4cAAahN5lUM3XqC-mLZ5XNi3qOmc3Gakalfs26X0CJ1pCC2QTZIE_KCIiw"; // Replace with the actual token
        token = "eyJraWQiOiIrRmpmbTJ6R2Q3YnlLU2kybk9cL0tZSVl0MGZuVFRGSzB1d0xKVzJ2dWVSaz0iLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiI1aHJraXAwNGh2NjdzbXJxNm80MWpja2JiNiIsInRva2VuX3VzZSI6ImFjY2VzcyIsInNjb3BlIjoiZGVmYXVsdC1tMm0tcmVzb3VyY2Utc2VydmVyLWN2Z3FkcFwvcmVhZCIsImF1dGhfdGltZSI6MTczODgwOTY1OSwiaXNzIjoiaHR0cHM6XC9cL2NvZ25pdG8taWRwLmFwLXNvdXRoZWFzdC0xLmFtYXpvbmF3cy5jb21cL2FwLXNvdXRoZWFzdC0xX1A2ZWdiMXMyYiIsImV4cCI6MTczODgxMzI1OSwiaWF0IjoxNzM4ODA5NjU5LCJ2ZXJzaW9uIjoyLCJqdGkiOiI0ZWI3ZmEyMC0zNzU2LTQwMzYtYmRhNS05Mzg1ZWZmNzVjNzQiLCJjbGllbnRfaWQiOiI1aHJraXAwNGh2NjdzbXJxNm80MWpja2JiNiJ9.DfW5Qqvr7OGc-GrJEv-3sjrvOAbAj1YEQj88lI4oR1ToTfX7YkudzalYoBHjJMEx7SuWC6eo5L1U3I1T3dhja1-EJ4llQq_V6tx84MQH6JSVi1SrPyyrQWAqkLMGSZdWZ0sdkBE-DapTNzNNwWDuDFm9q93oMmhGMVeN9TuOWEjnwON2riOUtuwwr7Dlqm5W9r6EdHEIdam35IxQSkb_yWeeqwuwb1zrn6x8Fk68Le5YY2RhiOxKeMoWmefIfbLIdti7iGLivB7xJ-uGevaMRT9t1IkoKhdaaNg-oc_KYfvod_iooh1PV-NHtlAizDEuRElyGIk4A1Ezxq8bAXzQAw";
        validateToken(token, Collections.singletonList("default-m2m-resource-server-cvgqdp/read"));
    }

    public static void validateToken(String token, List<String> requiredScopes) {
        try {
            // Fetch the JWKS
            String jwksJson = fetchJWKS();
            RSAPublicKey publicKey = getPublicKeyFromJWKS(jwksJson, token);

            // Create the JWT verifier
            Algorithm algorithm = Algorithm.RSA256(publicKey, null);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("https://cognito-idp." + REGION + ".amazonaws.com/" + USER_POOL_ID)
                    .build();

            // Verify the token
            DecodedJWT jwt = verifier.verify(token);
            System.out.println("Token is valid!");

            checkScope(jwt, requiredScopes);


            System.out.println("Subject: " + jwt.getSubject());

        } catch (Exception e) {
            System.out.println("Token is invalid: " + e.getMessage());
        }
    }

    private static void checkScope(DecodedJWT jwt, List<String> requiredScopes) throws Exception {
        String scopeClaim = jwt.getClaim("scope").asString();
        if (scopeClaim == null) {
            throw new Exception("No scope claim present in the token");
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
        throw new Exception("Required scope not present in the token");
    }

    private static String fetchJWKS() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(JWKS_URL.replace("{region}", REGION).replace("{userPoolId}", USER_POOL_ID))
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
}