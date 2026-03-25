package com.ryft.sdk.request;

import java.util.Map;

public record CreatePayoutRequest(Integer amount, String payoutMethodId, Map<String, Object> metadata) {
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

    public Builder metadata(Map<String, Object> metadata) {
      this.metadata = metadata;
      return this;
    }

    public CreatePayoutRequest build() {
      return new CreatePayoutRequest(amount, payoutMethodId, metadata);
    }
  }
}
