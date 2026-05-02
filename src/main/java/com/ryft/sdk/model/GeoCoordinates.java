package com.ryft.sdk.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Lat/long pair for an in-person location.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record GeoCoordinates(
    Double latitude,
    Double longitude
) {
}
