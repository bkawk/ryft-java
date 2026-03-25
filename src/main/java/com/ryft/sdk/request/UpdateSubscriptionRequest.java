package com.ryft.sdk.request;

import java.util.Map;

public record UpdateSubscriptionRequest(String description, Map<String, Object> metadata) {
  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    private String description;
    private Map<String, Object> metadata;

    public Builder description(String description) {
      this.description = description;
      return this;
    }

    public Builder metadata(Map<String, Object> metadata) {
      this.metadata = metadata;
      return this;
    }

    public UpdateSubscriptionRequest build() {
      return new UpdateSubscriptionRequest(description, metadata);
    }
  }
}
