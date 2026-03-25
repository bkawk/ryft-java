package com.ryft.sdk.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ryft.sdk.core.RyftConfig;
import com.ryft.sdk.core.RyftHttpClient;
import com.ryft.sdk.request.CreateCustomerRequest;
import com.ryft.sdk.request.CreatePaymentSessionRequest;
import com.ryft.sdk.request.UpdateSubscriptionRequest;
import com.ryft.sdk.testsupport.FakeHttpClient;
import java.util.Map;
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

    service.createForAccount(
        CreatePaymentSessionRequest.builder(500, "GBP")
            .customerEmail("buyer@example.test")
            .metadata(Map.of("source", "service-test"))
            .build(),
        "ac_123"
    );

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

  @Test
  void typedCustomerCreateUsesBuilderPayload() {
    FakeHttpClient fake = new FakeHttpClient(request -> FakeHttpClient.jsonResponse(200, "{\"id\":\"cus_123\",\"email\":\"ada@example.com\"}"));
    CustomersService service = new CustomersService(new RyftHttpClient(
        RyftConfig.builder("sk_sandbox_123").baseUrl("https://api.example.test/v1").httpClient(fake).build()
    ));

    var customer = service.create(
        CreateCustomerRequest.builder("ada@example.com")
            .firstName("Ada")
            .lastName("Lovelace")
            .metadata(Map.of("source", "typed"))
            .build()
    );

    assertEquals("cus_123", customer.id());
    assertEquals("ada@example.com", customer.email());
  }

  @Test
  void typedSubscriptionUpdateReturnsTypedModel() {
    FakeHttpClient fake = new FakeHttpClient(request -> FakeHttpClient.jsonResponse(200, "{\"id\":\"sub_123\",\"description\":\"Gold plan\"}"));
    SubscriptionsService service = new SubscriptionsService(new RyftHttpClient(
        RyftConfig.builder("sk_sandbox_123").baseUrl("https://api.example.test/v1").httpClient(fake).build()
    ));

    var subscription = service.update(
        "sub_123",
        UpdateSubscriptionRequest.builder()
            .description("Gold plan")
            .metadata(Map.of("tier", "gold"))
            .build()
    );

    assertEquals("sub_123", subscription.id());
    assertEquals("Gold plan", subscription.description());
  }
}
