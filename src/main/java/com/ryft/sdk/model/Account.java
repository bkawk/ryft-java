package com.ryft.sdk.model;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Connected account response model.
 */
public record Account(
    String id,
    AccountEntityType entityType,
    String email,
    JsonNode business,
    JsonNode individual,
    Metadata metadata
) {
}
