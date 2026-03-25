package com.ryft.sdk.model;

/**
 * Recurring interval configuration for a subscription price.
 */
public record SubscriptionInterval(SubscriptionIntervalUnit unit, Integer count, Integer times) {
}
