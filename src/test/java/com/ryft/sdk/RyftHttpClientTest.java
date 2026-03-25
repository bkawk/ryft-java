package com.ryft.sdk;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.ryft.sdk.core.QueryParams;
import com.ryft.sdk.core.RyftConfig;
import com.ryft.sdk.core.RyftHttpClient;
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
  void rejectsInvalidKeys() {
    assertThrows(IllegalArgumentException.class, () -> new RyftHttpClient(RyftConfig.builder("pk_test_123").build()));
  }

  @Test
  void preservesBooleanQueryContract() {
    String encoded = QueryParams.list(false, 50, "cus_123").encode();
    assertEquals("ascending=false&limit=50&startsAfter=cus_123", encoded);
  }
}
