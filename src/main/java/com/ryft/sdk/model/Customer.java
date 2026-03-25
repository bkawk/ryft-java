package com.ryft.sdk.model;

import java.util.Map;

public record Customer(
    String id,
    String email,
    String firstName,
    String lastName,
    Map<String, Object> metadata
) {
}
