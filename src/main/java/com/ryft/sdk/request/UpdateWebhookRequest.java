package com.ryft.sdk.request;

import java.util.List;

public record UpdateWebhookRequest(String url, Boolean active, List<String> eventTypes) {
  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    private String url;
    private Boolean active;
    private List<String> eventTypes;

    public Builder url(String url) {
      this.url = url;
      return this;
    }

    public Builder active(Boolean active) {
      this.active = active;
      return this;
    }

    public Builder eventTypes(List<String> eventTypes) {
      this.eventTypes = eventTypes;
      return this;
    }

    public UpdateWebhookRequest build() {
      return new UpdateWebhookRequest(url, active, eventTypes);
    }
  }
}
