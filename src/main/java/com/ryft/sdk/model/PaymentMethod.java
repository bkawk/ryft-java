package com.ryft.sdk.model;

public record PaymentMethod(
    String id,
    BillingAddress billingAddress
) {
}
