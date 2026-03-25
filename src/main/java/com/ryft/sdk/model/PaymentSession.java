package com.ryft.sdk.model;

import java.util.Map;

public record PaymentSession(
    String id,
    Integer amount,
    String currency,
    String customerEmail,
    Integer platformFee,
    Map<String, Object> metadata,
    PaymentSessionSplitDetail splitPaymentDetail
) {
}
