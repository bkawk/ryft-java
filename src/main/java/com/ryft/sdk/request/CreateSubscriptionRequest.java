package com.ryft.sdk.request;

import com.ryft.sdk.model.IdReference;
import com.ryft.sdk.model.SubscriptionInterval;
import com.ryft.sdk.model.SubscriptionPrice;
import java.util.Map;

public record CreateSubscriptionRequest(
    String description,
    IdReference customer,
    IdReference paymentMethod,
    Integer billingCycleTimestamp,
    SubscriptionPrice price,
    Map<String, Object> metadata,
    Map<String, Object> paymentSettings,
    Map<String, Object> shippingDetails
) {
  public static Builder builder(String customerId, String paymentMethodId) {
    return new Builder(customerId, paymentMethodId);
  }

  public static final class Builder {
    private String description = "SDK subscription readiness";
    private final String customerId;
    private final String paymentMethodId;
    private Integer billingCycleTimestamp;
    private SubscriptionPrice price = new SubscriptionPrice(100, "GBP", new SubscriptionInterval("Months", 1, 12));
    private Map<String, Object> metadata;
    private Map<String, Object> paymentSettings;
    private Map<String, Object> shippingDetails;

    private Builder(String customerId, String paymentMethodId) {
      this.customerId = customerId;
      this.paymentMethodId = paymentMethodId;
    }

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

    public Builder paymentSettings(Map<String, Object> paymentSettings) {
      this.paymentSettings = paymentSettings;
      return this;
    }

    public Builder shippingDetails(Map<String, Object> shippingDetails) {
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
