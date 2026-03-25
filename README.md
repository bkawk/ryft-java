# ryft-java

`ryft-java` is a Java SDK for the Ryft API, structured to mirror the mature backend SDKs already exercised by this workspace.

## Status

This repo is being built inside the `ryft-web-sdk-testing` harness to cover the same core capability families as the other backend SDKs:

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

## Requirements

- Java 21+
- Maven 3.9+

## Build

```bash
mvn test
```

## Basic Usage

```java
import com.ryft.sdk.RyftClient;
import java.util.Map;

RyftClient client = new RyftClient("sk_sandbox_...");
var customer = client.customers().create(Map.of(
    "email", "ada@example.com",
    "firstName", "Ada",
    "lastName", "Lovelace"
));
```
