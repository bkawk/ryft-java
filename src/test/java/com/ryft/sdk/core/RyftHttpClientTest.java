package com.ryft.sdk.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ryft.sdk.model.ApiError;
import com.ryft.sdk.model.ApiList;
import com.ryft.sdk.model.CaptureFlow;
import com.ryft.sdk.model.Customer;
import com.ryft.sdk.model.EntryMode;
import com.ryft.sdk.model.PaymentType;
import com.ryft.sdk.request.CreatePaymentSessionRequest;
import com.ryft.sdk.request.PaymentSessionSplitItemRequest;
import com.ryft.sdk.request.PaymentSessionSplitRequest;
import com.ryft.sdk.testsupport.FakeHttpClient;
import java.util.List;
import java.util.Map;
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

  @Test
  void typedApiListExposesPaginationHelpers() {
    ApiList<Customer> list = com.ryft.sdk.core.Json.MAPPER.convertValue(
        com.ryft.sdk.core.Json.MAPPER.valueToTree(java.util.Map.of(
            "items", java.util.List.of(java.util.Map.of("id", "cus_123", "email", "ada@example.com")),
            "paginationToken", "cus_next"
        )),
        com.ryft.sdk.core.Json.MAPPER.getTypeFactory().constructParametricType(ApiList.class, Customer.class)
    );

    assertEquals(1, list.items().size());
    assertTrue(list.hasNextPage());
    assertEquals("cus_next", list.nextPageRequest(50).startsAfter());
    assertEquals(50, list.nextPageRequest(50).limit());
  }

  @Test
  void typedApiListHandlesMissingPaginationToken() {
    ApiList<Customer> list = new ApiList<>(java.util.List.of(), null);
    assertFalse(list.hasNextPage());
  }

  @Test
  void typedRequestModelsSerializeEnumsAndNestedObjects() throws Exception {
    String json = Json.MAPPER.writeValueAsString(
        CreatePaymentSessionRequest.builder(500, "GBP")
            .customerEmail("buyer@example.test")
            .paymentType(PaymentType.Standard)
            .entryMode(EntryMode.Online)
            .captureFlow(CaptureFlow.Automatic)
            .splits(PaymentSessionSplitRequest.of(List.of(
                PaymentSessionSplitItemRequest.builder("ac_123", 500)
                    .description("platform split")
                    .fee(Map.of("amount", 25))
                    .build()
            )))
            .build()
    );

    assertTrue(json.contains("\"paymentType\":\"Standard\""));
    assertTrue(json.contains("\"entryMode\":\"Online\""));
    assertTrue(json.contains("\"captureFlow\":\"Automatic\""));
    assertTrue(json.contains("\"splits\":{\"items\":["));
  }
}
