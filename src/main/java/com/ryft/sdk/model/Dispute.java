package com.ryft.sdk.model;

import java.util.Map;

/**
 * Dispute response model.
 */
public record Dispute(
    String id,
    String status,
    String reason,
    Map<String, Object> metadata
) {
}
