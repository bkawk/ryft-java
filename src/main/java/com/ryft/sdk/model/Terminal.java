package com.ryft.sdk.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * In-person card terminal response.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Terminal(
    String id,
    String serialNumber,
    String locationId,
    String name,
    String status,
    Metadata metadata
) {
}
