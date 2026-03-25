package com.ryft.examples.basic;

import com.ryft.sdk.RyftClient;
import java.util.Map;

public final class BasicExample {
  private BasicExample() {
  }

  public static void main(String[] args) {
    String secretKey = System.getenv("RYFT_SECRET_KEY");
    if (secretKey == null || secretKey.isBlank()) {
      throw new IllegalStateException("Set RYFT_SECRET_KEY before running the example");
    }

    RyftClient client = new RyftClient(secretKey);
    var customer = client.customers().create(Map.of(
        "email", "sdk-example@example.test",
        "firstName", "Java",
        "lastName", "Example",
        "metadata", Map.of("source", "basic-example")
    ));

    System.out.println(customer.toPrettyString());
  }
}
