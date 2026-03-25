package com.ryft.sdk.model;

/**
 * Platform fee response model.
 */
public record PlatformFee(
    String id,
    Integer amount,
    String currency
) {
}
