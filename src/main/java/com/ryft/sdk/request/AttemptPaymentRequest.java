package com.ryft.sdk.request;

/**
 * Optional server-side payment attempt details for a payment session.
 */
public record AttemptPaymentRequest(PaymentMethodReference paymentMethod) {
  /**
   * Creates an attempt-payment payload from a payment-method reference.
   */
  public static AttemptPaymentRequest of(PaymentMethodReference paymentMethod) {
    return new AttemptPaymentRequest(paymentMethod);
  }
}
