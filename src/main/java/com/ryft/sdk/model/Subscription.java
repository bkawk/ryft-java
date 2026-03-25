package com.ryft.sdk.model;

import java.util.Map;

/**
 * Subscription response model.
 */
public record Subscription(
    String id,
    String description,
    IdReference customer,
    IdReference paymentMethod,
    SubscriptionBillingDetail billingDetail,
    Map<String, Object> metadata
) {
}
