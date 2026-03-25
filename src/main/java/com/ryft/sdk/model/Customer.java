package com.ryft.sdk.model;

import java.util.Map;

/**
 * Customer response model.
 */
public record Customer(
    String id,
    String email,
    String firstName,
    String lastName,
    Map<String, Object> metadata
) {
}
