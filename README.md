# ryft-java

Prerelease Java SDK for the Ryft API.

This repository focuses on a solid core API surface with live parity coverage, Java-native tests, and small examples for local development. The API may still evolve before a stable `v1` release.

## Installation

Until the package is published to a registry, install it locally from source:

```bash
mvn install
```

Then depend on it from another Maven project with:

```xml
<dependency>
  <groupId>com.ryft</groupId>
  <artifactId>ryft-java</artifactId>
  <version>0.1.0-SNAPSHOT</version>
</dependency>
```

## Quick Start

```java
import com.ryft.sdk.RyftClient;
import com.ryft.sdk.model.ApiError;
import com.ryft.sdk.request.CreateCustomerRequest;
import java.util.Map;

public final class Example {
  public static void main(String[] args) {
    RyftClient client = new RyftClient("sk_sandbox_your_secret_key");

    try {
      var customer = client.customers().create(
          CreateCustomerRequest.builder("sdk-example@example.test")
              .firstName("Java")
              .lastName("Example")
              .metadata(Map.of("source", "readme"))
              .build()
      );

      System.out.println(customer.id());
    } catch (ApiError error) {
      System.err.printf(
          "Ryft error: status=%d code=%s requestId=%s%n",
          error.getStatus(),
          error.getCode(),
          error.getRequestId()
      );
    }
  }
}
```

The repository also includes small example programs:

```bash
examples/basic/src/main/java/com/ryft/examples/basic/BasicExample.java
examples/http-jdk/src/main/java/com/ryft/examples/httpjdk/HttpServerExample.java
```

## Idiomatic Usage

The SDK keeps the raw `JsonNode` access patterns used by the parity harness, but it also exposes typed request builders and typed response models for the most common flows:

```java
import com.ryft.sdk.request.CreatePaymentSessionRequest;
import com.ryft.sdk.request.UpdateSubscriptionRequest;

var session = client.paymentSessions().create(
    CreatePaymentSessionRequest.builder(500, "GBP")
        .customerEmail("buyer@example.test")
        .paymentType("Standard")
        .entryMode("Online")
        .captureFlow("Automatic")
        .metadata(Map.of("source", "typed-example"))
        .build()
);

var subscription = client.subscriptions().update(
    "sub_123",
    UpdateSubscriptionRequest.builder()
        .description("Gold plan")
        .metadata(Map.of("tier", "gold"))
        .build()
);

System.out.println(session.id());
System.out.println(subscription.description());
```

## Configuration

`RyftClient` accepts either a secret key directly or a full `RyftConfig`:

- `secretKey`: required
- `baseUrl`: optional override for custom environments
- `httpClient`: optional custom `java.net.http.HttpClient`

If `baseUrl` is omitted, the SDK selects the Ryft sandbox or live API automatically from the secret key prefix.

```java
import com.ryft.sdk.RyftClient;
import com.ryft.sdk.core.RyftConfig;

RyftClient client = new RyftClient(
    RyftConfig.builder("sk_sandbox_your_secret_key")
        .baseUrl("https://sandbox-api.ryftpay.com/v1")
        .build()
);
```

## Status

This repo currently covers the same core capability families as the other backend SDKs already exercised by the workspace:

- customers
- payment sessions and refunds
- payment methods
- accounts and persons
- payout methods and payouts
- transfers
- balances and balance transactions
- account links
- webhooks
- subscriptions
- events
- platform fees
- files and disputes

## API Surface

- customers: create, list, get, update, delete
- payment sessions: create, get, update, refund
- payment-session transactions: list, get
- webhooks: create, list, get, update, delete
- accounts: create, get, verify, authorization links
- persons: create, list, get
- payout methods: create, list, get
- payouts: create, list, get
- transfers: create, list, get
- balances: list
- balance transactions: list
- payment methods: get, update, delete
- account links: create
- subscriptions: create, list, get, update, pause, resume, cancel
- subscription payment sessions: list
- events: list, get
- platform fees: list, get, refunds list
- files: create, list, get
- disputes: list, get, accept, challenge, add evidence, delete evidence

## Requirements

- Java 21+
- Maven 3.9+

## Credentials

The SDK requires a Ryft secret key.

Typical local usage:

```bash
export RYFT_SECRET_KEY=sk_sandbox_your_secret_key
```

## Error Handling

API failures raise `com.ryft.sdk.model.ApiError`, which includes:

- `status`
- `code`
- `message`
- `requestId`
- `errors`

Example:

```java
import com.ryft.sdk.model.ApiError;

try {
  client.customers().create(Map.of("email", ""));
} catch (ApiError error) {
  System.err.printf(
      "Ryft error: status=%d code=%s message=%s%n",
      error.getStatus(),
      error.getCode(),
      error.getMessage()
  );
}
```

## Development

```bash
mvn test
```

The repo also includes the parity CLI used by the multi-SDK harness:

```bash
mvn -q -DskipTests package dependency:build-classpath -Dmdep.outputFile=target/classpath.txt
java -cp "target/classes:$(cat target/classpath.txt)" com.ryft.sdk.cli.RyftDevCli customer-create sdk@example.test Java Example
```
