package com.ryft.sdk.model;

import java.util.Map;

/**
 * Connected-account person response model.
 */
public record Person(
    String id,
    String firstName,
    String lastName,
    String email,
    Map<String, Object> metadata
) {
}
