package com.ryft.sdk.request;

/**
 * Payment settings for subscription creation.
 */
public record SubscriptionPaymentSettings(StatementDescriptor statementDescriptor) {
  /**
   * Creates subscription payment settings from a statement descriptor.
   */
  public static SubscriptionPaymentSettings of(StatementDescriptor statementDescriptor) {
    return new SubscriptionPaymentSettings(statementDescriptor);
  }
}
