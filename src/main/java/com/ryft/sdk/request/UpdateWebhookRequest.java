package com.ryft.sdk.request;

import java.util.List;

public record UpdateWebhookRequest(String url, Boolean active, List<String> eventTypes) {
  /**
   * Starts a typed webhook-update builder.
   */
  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    private String url;
    private Boolean active;
    private List<String> eventTypes;

    /**
     * Sets the destination webhook URL.
     */
    public Builder url(String url) {
      this.url = url;
      return this;
    }

    /**
     * Sets whether the webhook should receive events.
     */
    public Builder active(Boolean active) {
      this.active = active;
      return this;
    }

    /**
     * Replaces the subscribed event types.
     */
    public Builder eventTypes(List<String> eventTypes) {
      this.eventTypes = eventTypes;
      return this;
    }

    /**
     * Builds the immutable request payload.
     */
    public UpdateWebhookRequest build() {
      return new UpdateWebhookRequest(url, active, eventTypes);
    }
  }
}
