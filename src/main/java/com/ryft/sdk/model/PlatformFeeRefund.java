package com.ryft.sdk.model;

/**
 * Platform fee refund response model.
 */
public record PlatformFeeRefund(
    String id,
    Integer amount,
    String currency
) {
}
