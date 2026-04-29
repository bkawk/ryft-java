package com.ryft.sdk.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Payment-session transaction or refund record.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record PaymentSessionTransaction(
    String id,
    String paymentSessionId,
    String type,
    Integer amount,
    String currency,
    String status,
    String reason,
    Integer refundedAmount
) {
}
