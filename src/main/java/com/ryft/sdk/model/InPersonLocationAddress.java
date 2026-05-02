package com.ryft.sdk.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Postal address attached to an in-person location.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record InPersonLocationAddress(
    String firstLine,
    String secondLine,
    String city,
    String region,
    String postalCode,
    String country
) {
}
