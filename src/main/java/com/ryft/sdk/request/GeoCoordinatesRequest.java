package com.ryft.sdk.request;

/**
 * Lat/long pair for an in-person location request.
 */
public record GeoCoordinatesRequest(
    Double latitude,
    Double longitude
) {
}
