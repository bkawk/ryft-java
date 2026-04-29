package com.ryft.sdk.request;

/**
 * Typed refund payload for a captured payment session.
 */
public record RefundPaymentSessionRequest(
    Integer amount,
    String reason,
    Boolean refundPlatformFee
) {
  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    private Integer amount;
    private String reason;
    private Boolean refundPlatformFee;

    /**
     * Sets the refund amount in the payment session currency's minor unit.
     */
    public Builder amount(Integer amount) {
      this.amount = amount;
      return this;
    }

    /**
     * Sets the refund reason expected by the Ryft API.
     */
    public Builder reason(String reason) {
      this.reason = reason;
      return this;
    }

    /**
     * Controls whether an associated platform fee should also be refunded.
     */
    public Builder refundPlatformFee(Boolean refundPlatformFee) {
      this.refundPlatformFee = refundPlatformFee;
      return this;
    }

    public RefundPaymentSessionRequest build() {
      return new RefundPaymentSessionRequest(amount, reason, refundPlatformFee);
    }
  }
}
