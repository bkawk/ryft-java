package com.ryft.sdk.model;

/**
 * Connected-account person response model.
 */
public record Person(
    String id,
    String firstName,
    String lastName,
    String email,
    Metadata metadata
) {
}
