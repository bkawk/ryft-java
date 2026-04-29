package com.ryft.sdk.model;

/**
 * Dispute response model.
 */
public record Dispute(
    String id,
    String status,
    String reason,
    Metadata metadata
) {
}
