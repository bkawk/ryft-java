package com.ryft.sdk.request;

/**
 * Wrapper for the requested amount on a terminal payment.
 */
public record RequestedAmounts(
    Integer requested
) {
}
