package com.ryft.sdk.request;

import java.util.Map;

public record UpdatePaymentSessionRequest(
    Integer amount,
    String customerEmail,
    String captureFlow,
    Map<String, Object> metadata
) {
  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    private Integer amount;
    private String customerEmail;
    private String captureFlow;
    private Map<String, Object> metadata;

    public Builder amount(Integer amount) {
      this.amount = amount;
      return this;
    }

    public Builder customerEmail(String customerEmail) {
      this.customerEmail = customerEmail;
      return this;
    }

    public Builder captureFlow(String captureFlow) {
      this.captureFlow = captureFlow;
      return this;
    }

    public Builder metadata(Map<String, Object> metadata) {
      this.metadata = metadata;
      return this;
    }

    public UpdatePaymentSessionRequest build() {
      return new UpdatePaymentSessionRequest(amount, customerEmail, captureFlow, metadata);
    }
  }
}
