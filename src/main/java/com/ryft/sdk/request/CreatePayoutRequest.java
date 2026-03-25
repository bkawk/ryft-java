package com.ryft.sdk.request;

import java.util.Map;

public record CreatePayoutRequest(Integer amount, String payoutMethodId, Map<String, Object> metadata) {
  /**
   * Starts a typed payout-create builder.
   */
  public static Builder builder(Integer amount, String payoutMethodId) {
    return new Builder(amount, payoutMethodId);
  }

  public static final class Builder {
    private final Integer amount;
    private final String payoutMethodId;
    private Map<String, Object> metadata;

    private Builder(Integer amount, String payoutMethodId) {
      this.amount = amount;
      this.payoutMethodId = payoutMethodId;
    }

    /**
     * Sets application-defined metadata for the payout.
     */
    public Builder metadata(Map<String, Object> metadata) {
      this.metadata = metadata;
      return this;
    }

    /**
     * Builds the immutable request payload.
     */
    public CreatePayoutRequest build() {
      return new CreatePayoutRequest(amount, payoutMethodId, metadata);
    }
  }
}
