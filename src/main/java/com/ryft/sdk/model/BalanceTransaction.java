package com.ryft.sdk.model;

/**
 * Balance transaction response model.
 */
public record BalanceTransaction(
    String id,
    Integer amount,
    String currency,
    String type,
    Metadata metadata
) {
}
