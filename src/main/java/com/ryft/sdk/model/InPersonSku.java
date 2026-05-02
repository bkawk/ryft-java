package com.ryft.sdk.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * In-person catalog SKU response.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record InPersonSku(
    String id,
    String productId,
    String country,
    String currency,
    Integer amount,
    String status,
    Metadata metadata
) {
}
