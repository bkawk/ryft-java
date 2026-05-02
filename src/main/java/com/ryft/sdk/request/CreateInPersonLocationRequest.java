package com.ryft.sdk.request;

import com.ryft.sdk.model.Metadata;

/**
 * Request payload to create an in-person location.
 */
public record CreateInPersonLocationRequest(
    String name,
    InPersonLocationAddressRequest address,
    GeoCoordinatesRequest geoCoordinates,
    Metadata metadata
) {
}
