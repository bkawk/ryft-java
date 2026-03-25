package com.ryft.sdk.model;

import java.util.Map;

/**
 * Connected account response model.
 */
public record Account(
    String id,
    String entityType,
    String email,
    Map<String, Object> metadata
) {
}
