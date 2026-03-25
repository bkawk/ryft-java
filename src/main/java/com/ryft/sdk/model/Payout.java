package com.ryft.sdk.model;

import java.util.Map;

public record Payout(
    String id,
    Integer amount,
    String currency,
    String status,
    Map<String, Object> metadata
) {
}
