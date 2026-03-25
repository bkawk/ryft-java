package com.ryft.sdk.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ryft.sdk.core.RyftConfig;
import com.ryft.sdk.core.RyftHttpClient;
import com.ryft.sdk.testsupport.FakeHttpClient;
import org.junit.jupiter.api.Test;

class ServiceRoutingTest {
  @Test
  void customerListBuildsExpectedQueryString() {
    FakeHttpClient fake = new FakeHttpClient(request -> FakeHttpClient.jsonResponse(200, "{\"items\":[]}"));
    CustomersService service = new CustomersService(new RyftHttpClient(
        RyftConfig.builder("sk_sandbox_123").baseUrl("https://api.example.test/v1").httpClient(fake).build()
    ));

    service.list("ada@example.com", 100, 200, false, 25, "cus_after");

    assertEquals(
        "https://api.example.test/v1/customers?ascending=false&limit=25&startsAfter=cus_after&email=ada%40example.com&startTimestamp=100&endTimestamp=200",
        fake.getLastRequest().uri().toString()
    );
  }

  @Test
  void paymentSessionCreateForAccountUsesAccountHeader() {
    FakeHttpClient fake = new FakeHttpClient(request -> FakeHttpClient.jsonResponse(200, "{\"id\":\"ps_123\"}"));
    PaymentSessionsService service = new PaymentSessionsService(new RyftHttpClient(
        RyftConfig.builder("sk_sandbox_123").baseUrl("https://api.example.test/v1").httpClient(fake).build()
    ));

    service.createForAccount(java.util.Map.of("amount", 500, "currency", "GBP"), "ac_123");

    assertEquals("https://api.example.test/v1/payment-sessions", fake.getLastRequest().uri().toString());
    assertEquals("ac_123", fake.getLastRequest().headers().firstValue("Account").orElseThrow());
  }

  @Test
  void subscriptionResumeHitsResumeEndpoint() {
    FakeHttpClient fake = new FakeHttpClient(request -> FakeHttpClient.jsonResponse(200, "{\"id\":\"sub_123\"}"));
    SubscriptionsService service = new SubscriptionsService(new RyftHttpClient(
        RyftConfig.builder("sk_sandbox_123").baseUrl("https://api.example.test/v1").httpClient(fake).build()
    ));

    service.resume("sub_123");

    assertEquals("PATCH", fake.getLastRequest().method());
    assertEquals("https://api.example.test/v1/subscriptions/sub_123/resume", fake.getLastRequest().uri().toString());
  }
}
