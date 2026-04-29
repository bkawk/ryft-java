package com.ryft.sdk.model;

/**
 * Payment-session transaction or refund record.
 */
public record PaymentSessionTransaction(
    String id,
    String type,
    Integer amount,
    String status,
    String reason,
    Integer refundedAmount
) {
}
