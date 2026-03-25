package com.ryft.sdk.model;

import java.util.Map;

/**
 * One split allocation returned from a payment session.
 */
public record PaymentSessionSplitItem(
    String accountId,
    Integer amount,
    String description,
    Map<String, Object> fee,
    Map<String, Object> metadata
) {
}
