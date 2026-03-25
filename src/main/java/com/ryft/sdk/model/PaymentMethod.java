package com.ryft.sdk.model;

/**
 * Saved payment method response model.
 */
public record PaymentMethod(
    String id,
    BillingAddress billingAddress
) {
}
