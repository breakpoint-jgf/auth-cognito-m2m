

**auth-cognito-m2m-client**
=====================

**Overview**
------------

auth-cognito-m2m-client is a Java library that provides a simple and easy-to-use interface for interacting with Amazon Cognito authentication services. It allows developers to easily obtain an access token for a machine-to-machine (M2M) client, which can then be used to access other secured services.

**Getting Started**
-------------------

### Prerequisites

* Java 8 or later
* Maven or Gradle for building and dependency management

### Installation

To use AuthCognitoClient in your project, add the following dependency to your `pom.xml` file (if using Maven):
```xml
<dependency>
    <groupId>com.safe.room.tech</groupId>
    <artifactId>auth-cognito-client</artifactId>
    <version>1.0.0</version>
</dependency>
```
Or, if using Gradle, add the following dependency to your `build.gradle` file:
```groovy
dependencies {
    implementation 'com.safe.room.tech:auth-cognito-client:1.0.0'
}
```
### Running Test Cases

Before running the test cases, please update the `test.properties` file in the `src/test/resources` directory with your own Cognito client ID, client secret, scope, and token endpoint.

Example `test.properties` file:
```properties
client.id=your_client_id
client.secret=your_client_secret
scopes=scope1,scope2,scope3
token.endpoint=https://your_cognito_domain.auth.us-east-1.amazoncognito.com/oauth2/token
```
Replace the placeholders with your own values.

Once you've updated the `test.properties` file, you can run the test cases using the following command:
```bash
mvn test
```
Or, if using Gradle:
```bash
gradle test
```
### Configuration

To use AuthCognitoClient, you will need to create an instance of the `AuthClientConfig` class and pass it to the `AuthCognitoClient` constructor. The `AuthClientConfig` class requires the following properties:

* `clientId`: The client ID of your Cognito user pool
* `clientSecret`: The client secret of your Cognito user pool
* `scopes`: The scope of the authentication request
* `tokenEndpoint`: The URL of the Cognito token endpoint

Here is an example of how to create an instance of `AuthClientConfig`:
```java
AuthClientConfig config = AuthClientConfig.builder()
        .setClientId("your_client_id")
        .setClientSecret("your_client_secret")
        .setScope("scope1,scope2,scope3")
        .setTokenEndpoint("https://your_cognito_domain.auth.us-east-1.amazoncognito.com/oauth2/token")
        .build();
```
### Usage

Once you have created an instance of `AuthCognitoClient`, you can use it to authenticate users and obtain access tokens. Here is an example of how to use the `getAccessToken` method:
```java
AuthCognitoClient client = new AuthCognitoClient(config);
AccessToken accessToken = client.getAccessToken();
System.out.println(accessToken.getToken());
```
**Testing**
------------

The `auth-cognito-m2m-client` module includes a set of unit tests to ensure the library is working correctly. You can run the tests using the following command:
```bash
mvn test
```
Or, if using Gradle:
```bash
gradle test
```
