package com.ryft.sdk.model;

/**
 * Connected account response model.
 */
public record Account(
    String id,
    AccountEntityType entityType,
    String email,
    Metadata metadata
) {
}
