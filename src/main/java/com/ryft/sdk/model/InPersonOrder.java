package com.ryft.sdk.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.List;

/**
 * In-person order response. Items, customer, shipping, and tracking are
 * intentionally untyped because their shape is broad and varies by feature.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record InPersonOrder(
    String id,
    String status,
    Integer amount,
    String currency,
    List<JsonNode> items,
    JsonNode customer,
    JsonNode shipping,
    JsonNode tracking
) {
}
