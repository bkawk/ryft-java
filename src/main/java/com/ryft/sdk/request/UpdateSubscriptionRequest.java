package com.ryft.sdk.request;

import com.ryft.sdk.model.Metadata;
import java.util.Map;

public record UpdateSubscriptionRequest(String description, Metadata metadata) {
  /**
   * Starts a typed subscription-update builder.
   */
  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    private String description;
    private Metadata metadata;

    /**
     * Sets the subscription description.
     */
    public Builder description(String description) {
      this.description = description;
      return this;
    }

    /**
     * Sets application-defined metadata for the subscription.
     */
    public Builder metadata(Metadata metadata) {
      this.metadata = metadata;
      return this;
    }

    /**
     * Sets application-defined metadata for the subscription.
     */
    public Builder metadata(Map<String, Object> metadata) {
      this.metadata = Metadata.of(metadata);
      return this;
    }

    /**
     * Builds the immutable request payload.
     */
    public UpdateSubscriptionRequest build() {
      return new UpdateSubscriptionRequest(description, metadata);
    }
  }
}
