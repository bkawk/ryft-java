package com.ryft.sdk.model;

public record BillingAddress(
    String lineOne,
    String lineTwo,
    String city,
    String region,
    String country,
    String postalCode
) {
}
