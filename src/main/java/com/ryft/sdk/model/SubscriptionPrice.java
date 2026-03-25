package com.ryft.sdk.model;

/**
 * Subscription pricing details.
 */
public record SubscriptionPrice(Integer amount, String currency, SubscriptionInterval interval) {
}
