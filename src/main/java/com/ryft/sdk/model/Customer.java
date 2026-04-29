package com.ryft.sdk.model;

/**
 * Customer response model.
 */
public record Customer(
    String id,
    String email,
    String firstName,
    String lastName,
    Metadata metadata
) {
}
