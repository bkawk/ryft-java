package com.ryft.examples.basic;

import com.ryft.sdk.RyftClient;
import com.ryft.sdk.core.Json;
import com.ryft.sdk.model.Metadata;
import com.ryft.sdk.request.CreateCustomerRequest;

public final class BasicExample {
  private BasicExample() {
  }

  public static void main(String[] args) {
    String secretKey = System.getenv("RYFT_SECRET_KEY");
    if (secretKey == null || secretKey.isBlank()) {
      throw new IllegalStateException("Set RYFT_SECRET_KEY before running the example");
    }

    RyftClient client = new RyftClient(secretKey);
    var customer = client.customers().create(
        CreateCustomerRequest.builder("sdk-example@example.test")
            .firstName("Java")
            .lastName("Example")
            .metadata(Metadata.of("source", "basic-example"))
            .build()
    );

    try {
      System.out.println(Json.MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(customer));
    } catch (Exception error) {
      throw new IllegalStateException("Failed to render customer response", error);
    }
  }
}
