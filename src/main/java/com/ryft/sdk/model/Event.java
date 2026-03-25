package com.ryft.sdk.model;

/**
 * Event response model.
 */
public record Event(
    String id,
    String type,
    Integer createdTimestamp
) {
}
