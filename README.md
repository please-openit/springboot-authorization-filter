# Springboot please-open.it authorization extension

*PleaseOpenItFilter is a Spring filter which checks on please-open.it server if access is enabled in the authorization platform.*

## Installation

Add PleaseOpenItFilter class to your project.

## Token exchange

```java
    public String exchangeToken(String provider, String token);
```

- provider : which provider where the token comes from
- token : an access token

returns a UUID, a token from please-open.it platform

## Properties

please-open-it.controllerId : controller ID, check it from [https://controllers.please-open.it](https://controllers.please-open.it)

please-open-it.provider : your access_token provider for token exchange

