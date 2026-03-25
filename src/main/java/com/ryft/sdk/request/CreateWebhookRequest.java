package com.ryft.sdk.request;

import java.util.List;

public record CreateWebhookRequest(String url, Boolean active, List<String> eventTypes) {
  /**
   * Creates a webhook-create request from simple values.
   */
  public static CreateWebhookRequest of(String url, Boolean active, List<String> eventTypes) {
    return new CreateWebhookRequest(url, active, eventTypes);
  }
}
