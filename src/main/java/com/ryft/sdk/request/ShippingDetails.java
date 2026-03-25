package com.ryft.sdk.request;

/**
 * Shipping details for subscription creation.
 */
public record ShippingDetails(Address address) {
  /**
   * Creates shipping details from a postal address.
   */
  public static ShippingDetails of(Address address) {
    return new ShippingDetails(address);
  }
}
