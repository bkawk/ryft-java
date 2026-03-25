package com.ryft.sdk.request;

import com.ryft.sdk.model.CaptureFlow;
import java.util.Map;

public record UpdatePaymentSessionRequest(
    Integer amount,
    String customerEmail,
    CaptureFlow captureFlow,
    Map<String, Object> metadata
) {
  /**
   * Starts a typed payment-session update builder.
   */
  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    private Integer amount;
    private String customerEmail;
    private CaptureFlow captureFlow;
    private Map<String, Object> metadata;

    /**
     * Sets the session amount.
     */
    public Builder amount(Integer amount) {
      this.amount = amount;
      return this;
    }

    /**
     * Sets the customer email used for the session.
     */
    public Builder customerEmail(String customerEmail) {
      this.customerEmail = customerEmail;
      return this;
    }

    /**
     * Sets the capture flow.
     */
    public Builder captureFlow(CaptureFlow captureFlow) {
      this.captureFlow = captureFlow;
      return this;
    }

    /**
     * Sets application-defined metadata for the session.
     */
    public Builder metadata(Map<String, Object> metadata) {
      this.metadata = metadata;
      return this;
    }

    /**
     * Builds the immutable request payload.
     */
    public UpdatePaymentSessionRequest build() {
      return new UpdatePaymentSessionRequest(amount, customerEmail, captureFlow, metadata);
    }
  }
}
