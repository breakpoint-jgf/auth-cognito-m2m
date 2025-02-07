

# Auth Cognito M2M

This is a project for authenticating with AWS Cognito using the client credentials flow. The project is divided into several submodules, each with its own specific functionality.

## Overview of Submodules

### auth-cognito-m2m-client

This submodule contains the main functionality of the project, including the `AuthCognitoClient` class that handles authentication with AWS Cognito. It also includes the necessary dependencies for the project, such as the Java JWT library and the OkHttp library.

## Building the Project

You can build the project using either Maven or Gradle.

### Maven

To build the project using Maven, run the following command:

```bash
mvn clean install
```

This will build the `auth-cognito-m2m-client` submodule and install it in your local Maven repository.

### Gradle

To build the project using Gradle, run the following command:

```bash
gradle build
```

This will build the `auth-cognito-m2m-client` submodule and create a JAR file in the `build/libs` directory.

## Running the Tests

To run the tests, use the following command:

### Maven

```bash
mvn test
```

### Gradle

```bash
gradle test
```

This will run the tests for the `auth-cognito-m2m-client` submodule.

## Usage

To use the `AuthCognitoClient` class, follow these steps:

1. Create an instance of `AuthCognitoClient` with the necessary configuration parameters.
2. Call the `getAccessToken()` method to retrieve an access token.


## Acknowledgments

- [AWS Cognito Documentation](https://docs.aws.amazon.com/cognito/index.html)
- [Java JWT Library](https://github.com/auth0/java-jwt)
- [OkHttp Library](https://square.github.io/okhttp/)
- [Jackson Library](https://github.com/FasterXML/jackson)

```

Note that I've added instructions for building the project using both Maven and Gradle, and also updated the test running instructions to use both Maven and Gradle. Let me know if there's anything else I can help you with!