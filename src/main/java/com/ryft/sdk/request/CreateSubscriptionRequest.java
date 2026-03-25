package com.ryft.sdk.request;

import com.ryft.sdk.model.IdReference;
import com.ryft.sdk.model.SubscriptionInterval;
import com.ryft.sdk.model.SubscriptionPrice;
import com.ryft.sdk.model.SubscriptionIntervalUnit;
import java.util.Map;

/**
 * Typed subscription create payload with a builder-style API.
 */
public record CreateSubscriptionRequest(
    String description,
    IdReference customer,
    IdReference paymentMethod,
    Integer billingCycleTimestamp,
    SubscriptionPrice price,
    Map<String, Object> metadata,
    SubscriptionPaymentSettings paymentSettings,
    ShippingDetails shippingDetails
) {
  public static Builder builder(String customerId, String paymentMethodId) {
    return new Builder(customerId, paymentMethodId);
  }

  public static final class Builder {
    private String description = "SDK subscription readiness";
    private final String customerId;
    private final String paymentMethodId;
    private Integer billingCycleTimestamp;
    private SubscriptionPrice price = new SubscriptionPrice(100, "GBP", new SubscriptionInterval(SubscriptionIntervalUnit.Months, 1, 12));
    private Map<String, Object> metadata;
    private SubscriptionPaymentSettings paymentSettings;
    private ShippingDetails shippingDetails;

    private Builder(String customerId, String paymentMethodId) {
      this.customerId = customerId;
      this.paymentMethodId = paymentMethodId;
    }

    /**
     * Sets the subscription description.
     */
    public Builder description(String description) {
      this.description = description;
      return this;
    }

    public Builder billingCycleTimestamp(Integer billingCycleTimestamp) {
      this.billingCycleTimestamp = billingCycleTimestamp;
      return this;
    }

    public Builder price(SubscriptionPrice price) {
      this.price = price;
      return this;
    }

    public Builder metadata(Map<String, Object> metadata) {
      this.metadata = metadata;
      return this;
    }

    /**
     * Sets subscription payment settings.
     */
    public Builder paymentSettings(SubscriptionPaymentSettings paymentSettings) {
      this.paymentSettings = paymentSettings;
      return this;
    }

    /**
     * Sets subscription shipping details.
     */
    public Builder shippingDetails(ShippingDetails shippingDetails) {
      this.shippingDetails = shippingDetails;
      return this;
    }

    public CreateSubscriptionRequest build() {
      return new CreateSubscriptionRequest(
          description,
          new IdReference(customerId),
          paymentMethodId == null || paymentMethodId.isBlank() ? null : new IdReference(paymentMethodId),
          billingCycleTimestamp,
          price,
          metadata,
          paymentSettings,
          shippingDetails
      );
    }
  }
}
