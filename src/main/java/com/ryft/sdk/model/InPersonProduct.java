package com.ryft.sdk.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * In-person catalog product response.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record InPersonProduct(
    String id,
    String name,
    String description,
    String status,
    Metadata metadata
) {
}
