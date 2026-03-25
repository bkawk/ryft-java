package com.ryft.sdk.model;

/**
 * Balance response model.
 */
public record Balance(
    String currency,
    Integer available,
    Integer pending
) {
}
