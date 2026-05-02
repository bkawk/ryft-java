package com.ryft.sdk.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * In-person retail location response.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record InPersonLocation(
    String id,
    String name,
    InPersonLocationAddress address,
    GeoCoordinates geoCoordinates,
    Metadata metadata
) {
}
