package com.ryft.sdk.model;

/**
 * Transfer response model.
 */
public record Transfer(
    String id,
    Integer amount,
    String currency,
    String destinationAccountId,
    Metadata metadata
) {
}
