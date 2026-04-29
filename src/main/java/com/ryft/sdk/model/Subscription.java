package com.ryft.sdk.model;

/**
 * Subscription response model.
 */
public record Subscription(
    String id,
    String description,
    IdReference customer,
    IdReference paymentMethod,
    SubscriptionBillingDetail billingDetail,
    Metadata metadata
) {
}
