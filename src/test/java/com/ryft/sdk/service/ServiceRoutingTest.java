package com.ryft.sdk.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ryft.sdk.core.RyftConfig;
import com.ryft.sdk.core.RyftHttpClient;
import com.ryft.sdk.model.AccountEntityType;
import com.ryft.sdk.model.Balance;
import com.ryft.sdk.model.BillingAddress;
import com.ryft.sdk.model.CaptureFlow;
import com.ryft.sdk.model.EntryMode;
import com.ryft.sdk.model.Event;
import com.ryft.sdk.model.PaymentType;
import com.ryft.sdk.model.PlatformFee;
import com.ryft.sdk.model.RyftFile;
import com.ryft.sdk.request.CreateAccountAuthorizationRequest;
import com.ryft.sdk.request.CreateAccountRequest;
import com.ryft.sdk.request.CreateCustomerRequest;
import com.ryft.sdk.request.CreatePaymentSessionRequest;
import com.ryft.sdk.request.CreateWebhookRequest;
import com.ryft.sdk.request.PageRequest;
import com.ryft.sdk.request.TimeRangePageRequest;
import com.ryft.sdk.request.UpdateSubscriptionRequest;
import com.ryft.sdk.request.UpdatePaymentMethodRequest;
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
            .paymentType(PaymentType.Standard)
            .entryMode(EntryMode.Online)
            .captureFlow(CaptureFlow.Automatic)
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

  @Test
  void typedAccountCreateReturnsTypedAccount() {
    FakeHttpClient fake = new FakeHttpClient(request -> FakeHttpClient.jsonResponse(200, "{\"id\":\"ac_123\",\"entityType\":\"Business\",\"email\":\"biz@example.com\"}"));
    AccountsService service = new AccountsService(new RyftHttpClient(
        RyftConfig.builder("sk_sandbox_123").baseUrl("https://api.example.test/v1").httpClient(fake).build()
    ));

    var account = service.create(
        CreateAccountRequest.builder(AccountEntityType.Business, "biz@example.com")
            .metadata(Map.of("source", "typed"))
            .build()
    );

    assertEquals("ac_123", account.id());
    assertEquals("Business", account.entityType());
    assertEquals("biz@example.com", account.email());
  }

  @Test
  void typedAccountAuthLinkReturnsTypedResponse() {
    FakeHttpClient fake = new FakeHttpClient(request -> FakeHttpClient.jsonResponse(200, "{\"url\":\"https://example.test/auth\"}"));
    AccountsService service = new AccountsService(new RyftHttpClient(
        RyftConfig.builder("sk_sandbox_123").baseUrl("https://api.example.test/v1").httpClient(fake).build()
    ));

    var response = service.createAuthLink(CreateAccountAuthorizationRequest.of("owner@example.com", "https://example.com/return"));

    assertEquals("https://example.test/auth", response.url());
  }

  @Test
  void typedWebhookCreateReturnsTypedWebhook() {
    FakeHttpClient fake = new FakeHttpClient(request -> FakeHttpClient.jsonResponse(
        200,
        "{\"id\":\"wh_123\",\"url\":\"https://example.com/hook\",\"active\":true,\"eventTypes\":[\"PaymentSession.captured\"]}"
    ));
    WebhooksService service = new WebhooksService(new RyftHttpClient(
        RyftConfig.builder("sk_sandbox_123").baseUrl("https://api.example.test/v1").httpClient(fake).build()
    ));

    var webhook = service.create(CreateWebhookRequest.of("https://example.com/hook", true, java.util.List.of("PaymentSession.captured")));

    assertEquals("wh_123", webhook.id());
    assertEquals("https://example.com/hook", webhook.url());
    assertEquals(true, webhook.active());
  }

  @Test
  void typedPaymentMethodUpdateReturnsTypedPaymentMethod() {
    FakeHttpClient fake = new FakeHttpClient(request -> FakeHttpClient.jsonResponse(
        200,
        "{\"id\":\"pm_123\",\"billingAddress\":{\"lineOne\":\"1 SDK Street\",\"city\":\"London\",\"country\":\"GB\",\"postalCode\":\"SW1A1AA\"}}"
    ));
    PaymentMethodsService service = new PaymentMethodsService(new RyftHttpClient(
        RyftConfig.builder("sk_sandbox_123").baseUrl("https://api.example.test/v1").httpClient(fake).build()
    ));

    var paymentMethod = service.update(
        "pm_123",
        UpdatePaymentMethodRequest.of(new BillingAddress("1 SDK Street", null, "London", null, "GB", "SW1A1AA"))
    );

    assertEquals("pm_123", paymentMethod.id());
    assertEquals("London", paymentMethod.billingAddress().city());
  }

  @Test
  void typedCustomerListAcceptsPageRequest() {
    FakeHttpClient fake = new FakeHttpClient(request -> FakeHttpClient.jsonResponse(200, "{\"items\":[],\"paginationToken\":\"cus_next\"}"));
    CustomersService service = new CustomersService(new RyftHttpClient(
        RyftConfig.builder("sk_sandbox_123").baseUrl("https://api.example.test/v1").httpClient(fake).build()
    ));

    var list = service.listCustomers(
        "ada@example.com",
        100,
        200,
        PageRequest.builder().ascending(true).limit(25).startsAfter("cus_prev").build()
    );

    assertEquals(
        "https://api.example.test/v1/customers?ascending=true&limit=25&startsAfter=cus_prev&email=ada%40example.com&startTimestamp=100&endTimestamp=200",
        fake.getLastRequest().uri().toString()
    );
    assertEquals("cus_next", list.paginationToken());
  }

  @Test
  void typedSubscriptionListAcceptsTimeRangePageRequest() {
    FakeHttpClient fake = new FakeHttpClient(request -> FakeHttpClient.jsonResponse(200, "{\"items\":[]}"));
    SubscriptionsService service = new SubscriptionsService(new RyftHttpClient(
        RyftConfig.builder("sk_sandbox_123").baseUrl("https://api.example.test/v1").httpClient(fake).build()
    ));

    service.listSubscriptions(
        TimeRangePageRequest.builder()
            .startTimestamp(100)
            .endTimestamp(200)
            .ascending(false)
            .limit(10)
            .startsAfter("sub_prev")
            .build()
    );

    assertEquals(
        "https://api.example.test/v1/subscriptions?ascending=false&limit=10&startsAfter=sub_prev&startTimestamp=100&endTimestamp=200",
        fake.getLastRequest().uri().toString()
    );
  }

  @Test
  void typedEventReadUsesTypedModel() {
    FakeHttpClient fake = new FakeHttpClient(request -> FakeHttpClient.jsonResponse(200, "{\"id\":\"evt_123\",\"type\":\"PaymentSession.captured\",\"createdTimestamp\":1710000000}"));
    EventsService service = new EventsService(new RyftHttpClient(
        RyftConfig.builder("sk_sandbox_123").baseUrl("https://api.example.test/v1").httpClient(fake).build()
    ));

    Event event = service.getEvent("evt_123", null);

    assertEquals("evt_123", event.id());
    assertEquals("PaymentSession.captured", event.type());
  }

  @Test
  void typedFileListUsesTypedPageResponse() {
    FakeHttpClient fake = new FakeHttpClient(request -> FakeHttpClient.jsonResponse(200, "{\"items\":[{\"id\":\"file_123\",\"category\":\"dispute_evidence\",\"fileName\":\"evidence.pdf\"}],\"paginationToken\":\"file_next\"}"));
    FilesService service = new FilesService(new RyftHttpClient(
        RyftConfig.builder("sk_sandbox_123").baseUrl("https://api.example.test/v1").httpClient(fake).build()
    ));

    var files = service.listFiles("dispute_evidence", PageRequest.firstPage(10));

    assertEquals("file_123", files.items().getFirst().id());
    assertEquals("file_next", files.paginationToken());
  }

  @Test
  void typedDisputeListUsesTypedPageResponse() {
    FakeHttpClient fake = new FakeHttpClient(request -> FakeHttpClient.jsonResponse(200, "{\"items\":[{\"id\":\"dp_123\",\"status\":\"Open\",\"reason\":\"Fraudulent\"}]}"));
    DisputesService service = new DisputesService(new RyftHttpClient(
        RyftConfig.builder("sk_sandbox_123").baseUrl("https://api.example.test/v1").httpClient(fake).build()
    ));

    var disputes = service.listDisputes(TimeRangePageRequest.builder().limit(10).build());

    assertEquals("dp_123", disputes.items().getFirst().id());
    assertEquals("Open", disputes.items().getFirst().status());
  }

  @Test
  void typedBalancesListUsesTypedModels() {
    FakeHttpClient fake = new FakeHttpClient(request -> FakeHttpClient.jsonResponse(200, "{\"items\":[{\"currency\":\"GBP\",\"available\":1000,\"pending\":50}]}"));
    BalancesService service = new BalancesService(new RyftHttpClient(
        RyftConfig.builder("sk_sandbox_123").baseUrl("https://api.example.test/v1").httpClient(fake).build()
    ));

    var balances = service.listBalances("GBP", "ac_123");
    Balance balance = balances.items().getFirst();

    assertEquals("GBP", balance.currency());
    assertEquals(1000, balance.available());
  }

  @Test
  void typedPlatformFeeReadUsesTypedModel() {
    FakeHttpClient fake = new FakeHttpClient(request -> FakeHttpClient.jsonResponse(200, "{\"id\":\"fee_123\",\"amount\":50,\"currency\":\"GBP\"}"));
    PlatformFeesService service = new PlatformFeesService(new RyftHttpClient(
        RyftConfig.builder("sk_sandbox_123").baseUrl("https://api.example.test/v1").httpClient(fake).build()
    ));

    PlatformFee fee = service.getPlatformFee("fee_123");

    assertEquals("fee_123", fee.id());
    assertEquals(50, fee.amount());
  }
}
