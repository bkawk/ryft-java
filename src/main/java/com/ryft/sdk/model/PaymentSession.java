package com.ryft.sdk.model;

/**
 * Payment session response model.
 */
public record PaymentSession(
    String id,
    Integer amount,
    String currency,
    String customerEmail,
    Integer platformFee,
    Metadata metadata,
    PaymentSessionSplitDetail splitPaymentDetail
) {
}
