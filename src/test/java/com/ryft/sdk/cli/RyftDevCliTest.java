package com.ryft.sdk.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class RyftDevCliTest {
  @Test
  void paymentSessionCreateRequestWrapsReferencesAndPreservesSplitShape() {
    Map<String, Object> options = new LinkedHashMap<>();
    options.put("accountId", "ac_123");
    options.put("customerId", "cus_123");
    options.put("previousPaymentId", "ps_prev");
    options.put("splits", Map.of("items", List.of(Map.of("accountId", "ac_split", "amount", 500))));

    RyftDevCli.AccountScopedRequest request = RyftDevCli.buildPaymentSessionCreateRequest(options);

    assertEquals("ac_123", request.accountId());
    assertEquals("cus_123", ((Map<?, ?>) request.request().get("customerDetails")).get("id"));
    assertEquals("ps_prev", ((Map<?, ?>) request.request().get("previousPayment")).get("id"));
    assertEquals("https://example.com/return", request.request().get("returnUrl"));
    assertEquals(1, ((List<?>) ((Map<?, ?>) request.request().get("splits")).get("items")).size());
  }

  @Test
  void paymentSessionCreateRequestWrapsSplitListsOnce() {
    Map<String, Object> options = new LinkedHashMap<>();
    options.put("splits", List.of(Map.of("accountId", "ac_split", "amount", 500)));

    RyftDevCli.AccountScopedRequest request = RyftDevCli.buildPaymentSessionCreateRequest(options);

    assertEquals(1, ((List<?>) ((Map<?, ?>) request.request().get("splits")).get("items")).size());
  }

  @Test
  void subscriptionCreateDefaultsMatchBackendParityContract() {
    Map<String, Object> request = RyftDevCli.buildSubscriptionCreateRequest("cus_123", "pm_123", Map.of());

    assertEquals("cus_123", ((Map<?, ?>) request.get("customer")).get("id"));
    assertEquals("pm_123", ((Map<?, ?>) request.get("paymentMethod")).get("id"));
    assertEquals("SDK subscription readiness", request.get("description"));
    Map<?, ?> price = (Map<?, ?>) request.get("price");
    Map<?, ?> interval = (Map<?, ?>) price.get("interval");
    assertEquals("Months", interval.get("unit"));
    assertEquals(1, interval.get("count"));
    assertEquals(12, interval.get("times"));
  }

  @Test
  void subscriptionCreateSkipsBlankPaymentMethodReference() {
    Map<String, Object> request = RyftDevCli.buildSubscriptionCreateRequest("cus_123", "", Map.of());

    assertFalse(request.containsKey("paymentMethod"));
  }
}
