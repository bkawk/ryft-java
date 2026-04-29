package com.ryft.sdk;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ryft.sdk.core.RyftConfig;
import com.ryft.sdk.model.AccountEntityType;
import com.ryft.sdk.model.Metadata;
import com.ryft.sdk.model.PaymentType;
import com.ryft.sdk.request.CreateAccountRequest;
import com.ryft.sdk.request.CreateCustomerRequest;
import com.ryft.sdk.request.CreatePaymentSessionRequest;
import com.ryft.sdk.request.RefundPaymentSessionRequest;
import com.ryft.sdk.request.UpdatePersonRequest;
import com.ryft.sdk.testsupport.FakeHttpClient;
import org.junit.jupiter.api.Test;

class RyftClientWorkflowTest {
  @Test
  void clientSupportsTypedCustomerPaymentSessionAndRefundWorkflow() {
    FakeHttpClient fake = new FakeHttpClient(request -> {
      String method = request.method();
      String path = request.uri().getPath();
      return switch (method + " " + path) {
        case "POST /v1/customers" -> FakeHttpClient.jsonResponse(
            200,
            "{\"id\":\"cus_123\",\"email\":\"ada@example.com\",\"metadata\":{\"source\":\"workflow\"}}"
        );
        case "POST /v1/payment-sessions" -> FakeHttpClient.jsonResponse(
            200,
            "{\"id\":\"ps_123\",\"amount\":500,\"currency\":\"GBP\",\"customerEmail\":\"ada@example.com\",\"metadata\":{\"source\":\"workflow\"}}"
        );
        case "POST /v1/payment-sessions/ps_123/refunds" -> FakeHttpClient.jsonResponse(
            200,
            "{\"id\":\"txn_123\",\"type\":\"Refund\",\"amount\":100,\"status\":\"Succeeded\",\"reason\":\"RequestedByCustomer\",\"refundedAmount\":100}"
        );
        default -> throw new IllegalStateException("Unexpected request: " + method + " " + path);
      };
    });

    RyftClient client = new RyftClient(
        RyftConfig.builder("sk_sandbox_123").baseUrl("https://api.example.test/v1").httpClient(fake).build()
    );

    var customer = client.customers().create(
        CreateCustomerRequest.builder("ada@example.com")
            .firstName("Ada")
            .lastName("Lovelace")
            .metadata(Metadata.of("source", "workflow"))
            .build()
    );
    assertEquals("cus_123", customer.id());
    assertEquals("workflow", customer.metadata().get("source"));

    var session = client.paymentSessions().create(
        CreatePaymentSessionRequest.builder(500, "GBP")
            .customerEmail("ada@example.com")
            .paymentType(PaymentType.Standard)
            .metadata(Metadata.of("source", "workflow"))
            .build()
    );
    assertEquals("ps_123", session.id());
    assertEquals("workflow", session.metadata().get("source"));

    var refund = client.paymentSessions().refund(
        "ps_123",
        RefundPaymentSessionRequest.builder()
            .amount(100)
            .reason("RequestedByCustomer")
            .refundPlatformFee(Boolean.TRUE)
            .build()
    );
    assertEquals("txn_123", refund.id());
    assertEquals("Refund", refund.type());
    assertEquals(100, refund.refundedAmount());
  }

  @Test
  void clientSupportsTypedAccountPersonAndDisputeWorkflow() {
    FakeHttpClient fake = new FakeHttpClient(request -> {
      String method = request.method();
      String path = request.uri().getPath();
      return switch (method + " " + path) {
        case "POST /v1/accounts" -> FakeHttpClient.jsonResponse(
            200,
            "{\"id\":\"ac_123\",\"entityType\":\"Business\",\"email\":\"biz@example.com\",\"metadata\":{\"source\":\"workflow\"}}"
        );
        case "PATCH /v1/accounts/ac_123/persons/pe_123" -> FakeHttpClient.jsonResponse(
            200,
            "{\"id\":\"pe_123\",\"firstName\":\"Ada\",\"lastName\":\"Lovelace\",\"email\":\"ada@example.com\",\"metadata\":{\"role\":\"director\"}}"
        );
        case "POST /v1/disputes/dp_123/accept" -> FakeHttpClient.jsonResponse(
            200,
            "{\"id\":\"dp_123\",\"status\":\"Accepted\",\"reason\":\"Fraud\",\"metadata\":{\"owner\":\"risk\"}}"
        );
        default -> throw new IllegalStateException("Unexpected request: " + method + " " + path);
      };
    });

    RyftClient client = new RyftClient(
        RyftConfig.builder("sk_sandbox_123").baseUrl("https://api.example.test/v1").httpClient(fake).build()
    );

    var account = client.accounts().create(
        CreateAccountRequest.builder(AccountEntityType.Business, "biz@example.com")
            .metadata(Metadata.of("source", "workflow"))
            .build()
    );
    assertEquals("ac_123", account.id());
    assertEquals("workflow", account.metadata().get("source"));

    var person = client.persons().update(
        "ac_123",
        "pe_123",
        UpdatePersonRequest.builder()
            .firstName("Ada")
            .lastName("Lovelace")
            .email("ada@example.com")
            .metadata(Metadata.of("role", "director"))
            .build()
    );
    assertEquals("pe_123", person.id());
    assertEquals("director", person.metadata().get("role"));

    var dispute = client.disputes().accept("dp_123");
    assertEquals("dp_123", dispute.id());
    assertEquals("Accepted", dispute.status());
    assertEquals("risk", dispute.metadata().get("owner"));
  }
}
