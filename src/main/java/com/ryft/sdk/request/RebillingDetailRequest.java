package com.ryft.sdk.request;

/**
 * Rebilling details for recurring or subsequent payment-session creation.
 */
public record RebillingDetailRequest(
    String amountVariance,
    Integer numberOfDaysBetweenPayments,
    Integer totalNumberOfPayments,
    Integer currentPaymentNumber
) {
  /**
   * Starts a typed rebilling-detail builder.
   */
  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    private String amountVariance;
    private Integer numberOfDaysBetweenPayments;
    private Integer totalNumberOfPayments;
    private Integer currentPaymentNumber;

    /**
     * Sets the permitted amount variance mode.
     */
    public Builder amountVariance(String amountVariance) {
      this.amountVariance = amountVariance;
      return this;
    }

    /**
     * Sets the spacing between recurring payment attempts in days.
     */
    public Builder numberOfDaysBetweenPayments(Integer numberOfDaysBetweenPayments) {
      this.numberOfDaysBetweenPayments = numberOfDaysBetweenPayments;
      return this;
    }

    /**
     * Sets the total number of planned payments.
     */
    public Builder totalNumberOfPayments(Integer totalNumberOfPayments) {
      this.totalNumberOfPayments = totalNumberOfPayments;
      return this;
    }

    /**
     * Sets the current payment number within the recurring series.
     */
    public Builder currentPaymentNumber(Integer currentPaymentNumber) {
      this.currentPaymentNumber = currentPaymentNumber;
      return this;
    }

    /**
     * Builds the immutable rebilling-detail payload.
     */
    public RebillingDetailRequest build() {
      return new RebillingDetailRequest(
          amountVariance,
          numberOfDaysBetweenPayments,
          totalNumberOfPayments,
          currentPaymentNumber
      );
    }
  }
}
