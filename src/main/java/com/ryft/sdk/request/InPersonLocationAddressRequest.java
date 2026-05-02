package com.ryft.sdk.request;

/**
 * Postal address payload for an in-person location request.
 */
public record InPersonLocationAddressRequest(
    String firstLine,
    String secondLine,
    String city,
    String region,
    String postalCode,
    String country
) {
}
