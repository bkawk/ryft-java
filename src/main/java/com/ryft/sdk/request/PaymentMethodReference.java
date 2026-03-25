package com.ryft.sdk.request;

/**
 * Reference to an existing payment method.
 */
public record PaymentMethodReference(String id) {
  /**
   * Creates a payment-method reference from an existing identifier.
   */
  public static PaymentMethodReference of(String id) {
    return new PaymentMethodReference(id);
  }
}
