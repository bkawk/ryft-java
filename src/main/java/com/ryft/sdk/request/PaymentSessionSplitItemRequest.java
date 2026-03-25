package com.ryft.sdk.request;

import java.util.Map;

/**
 * One destination allocation within a split payment request.
 */
public record PaymentSessionSplitItemRequest(
    String accountId,
    Integer amount,
    String description,
    Map<String, Object> fee,
    Map<String, Object> metadata
) {
  public static Builder builder(String accountId, Integer amount) {
    return new Builder(accountId, amount);
  }

  public static final class Builder {
    private final String accountId;
    private final Integer amount;
    private String description;
    private Map<String, Object> fee;
    private Map<String, Object> metadata;

    private Builder(String accountId, Integer amount) {
      this.accountId = accountId;
      this.amount = amount;
    }

    public Builder description(String description) {
      this.description = description;
      return this;
    }

    public Builder fee(Map<String, Object> fee) {
      this.fee = fee;
      return this;
    }

    public Builder metadata(Map<String, Object> metadata) {
      this.metadata = metadata;
      return this;
    }

    public PaymentSessionSplitItemRequest build() {
      return new PaymentSessionSplitItemRequest(accountId, amount, description, fee, metadata);
    }
  }
}
