package com.ryft.sdk.model;

import java.util.Map;

public record Person(
    String id,
    String firstName,
    String lastName,
    String email,
    Map<String, Object> metadata
) {
}
