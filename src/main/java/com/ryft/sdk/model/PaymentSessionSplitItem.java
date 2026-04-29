package com.ryft.sdk.model;

/**
 * One split allocation returned from a payment session.
 */
public record PaymentSessionSplitItem(
    String accountId,
    Integer amount,
    String description,
    Fee fee,
    Metadata metadata
) {
}
