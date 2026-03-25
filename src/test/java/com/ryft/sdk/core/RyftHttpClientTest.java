package com.ryft.sdk.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.ryft.sdk.model.ApiError;
import com.ryft.sdk.testsupport.FakeHttpClient;
import org.junit.jupiter.api.Test;

class RyftHttpClientTest {
  @Test
  void usesSandboxBaseUrlForSandboxKeys() {
    RyftHttpClient client = new RyftHttpClient(RyftConfig.builder("sk_sandbox_123").build());
    assertEquals(RyftHttpClient.SANDBOX_BASE_URL, client.getBaseUrl());
  }

  @Test
  void usesLiveBaseUrlForLiveKeys() {
    RyftHttpClient client = new RyftHttpClient(RyftConfig.builder("sk_live_123").build());
    assertEquals(RyftHttpClient.LIVE_BASE_URL, client.getBaseUrl());
  }

  @Test
  void customBaseUrlIsNormalized() {
    RyftHttpClient client = new RyftHttpClient(RyftConfig.builder("sk_sandbox_123").baseUrl("https://api.example.test/v1/").build());
    assertEquals("https://api.example.test/v1", client.getBaseUrl());
  }

  @Test
  void rejectsInvalidKeys() {
    assertThrows(IllegalArgumentException.class, () -> new RyftHttpClient(RyftConfig.builder("pk_test_123").build()));
  }

  @Test
  void preservesBooleanQueryContract() {
    String encoded = QueryParams.list(false, 50, "cus_123").encode();
    assertEquals("ascending=false&limit=50&startsAfter=cus_123", encoded);
  }

  @Test
  void serializesJsonAndAccountHeaders() {
    FakeHttpClient fake = new FakeHttpClient(request -> FakeHttpClient.jsonResponse(200, "{\"id\":\"cus_123\"}"));
    RyftHttpClient client = new RyftHttpClient(RyftConfig.builder("sk_sandbox_123").baseUrl("https://api.example.test/v1").httpClient(fake).build());

    client.post("customers", java.util.Map.of("email", "ada@example.com"), "ac_123");

    assertEquals("https://api.example.test/v1/customers", fake.getLastRequest().uri().toString());
    assertEquals("ac_123", fake.getLastRequest().headers().firstValue("Account").orElseThrow());
    assertEquals("{\"email\":\"ada@example.com\"}", fake.getLastBody());
  }

  @Test
  void parsesStructuredApiErrors() {
    FakeHttpClient fake = new FakeHttpClient(request -> FakeHttpClient.jsonResponse(
        400,
        "{\"requestId\":\"req_123\",\"code\":\"400\",\"errors\":[{\"code\":\"invalid_field\",\"message\":\"email is required\"}]}"
    ));
    RyftHttpClient client = new RyftHttpClient(RyftConfig.builder("sk_sandbox_123").baseUrl("https://api.example.test/v1").httpClient(fake).build());

    ApiError error = assertThrows(ApiError.class, () -> client.get("customers", null, null));

    assertEquals(400, error.getStatus());
    assertEquals("400", error.getCode());
    assertEquals("req_123", error.getRequestId());
    assertEquals("invalid_field", error.getErrors().getFirst().code());
    assertEquals("email is required", error.getErrors().getFirst().message());
  }
}
