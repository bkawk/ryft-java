package com.ryft.sdk.request;

/**
 * Card-statement descriptor details for recurring billing.
 */
public record StatementDescriptor(String descriptor, String city) {
  /**
   * Creates a statement descriptor payload from simple values.
   */
  public static StatementDescriptor of(String descriptor, String city) {
    return new StatementDescriptor(descriptor, city);
  }
}
