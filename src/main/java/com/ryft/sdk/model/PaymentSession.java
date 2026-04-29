package com.ryft.sdk.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Payment session response model.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record PaymentSession(
    String id,
    String status,
    Integer amount,
    String currency,
    String customerEmail,
    String paymentType,
    String entryMode,
    String captureFlow,
    String authorizationType,
    Integer platformFee,
    Metadata metadata,
    PaymentSessionSplitDetail splitPaymentDetail,
    JsonNode paymentMethod,
    JsonNode requiredAction,
    JsonNode lastError
) {
}
