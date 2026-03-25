package com.ryft.sdk.request;

import java.util.Map;

public record CreateTransferRequest(
    String destinationAccountId,
    Integer amount,
    String currency,
    Map<String, Object> metadata
) {
  public static Builder builder(String destinationAccountId, Integer amount, String currency) {
    return new Builder(destinationAccountId, amount, currency);
  }

  public static final class Builder {
    private final String destinationAccountId;
    private final Integer amount;
    private final String currency;
    private Map<String, Object> metadata;

    private Builder(String destinationAccountId, Integer amount, String currency) {
      this.destinationAccountId = destinationAccountId;
      this.amount = amount;
      this.currency = currency;
    }

    public Builder metadata(Map<String, Object> metadata) {
      this.metadata = metadata;
      return this;
    }

    public CreateTransferRequest build() {
      return new CreateTransferRequest(destinationAccountId, amount, currency, metadata);
    }
  }
}
