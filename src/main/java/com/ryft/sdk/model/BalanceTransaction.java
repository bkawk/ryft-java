package com.ryft.sdk.model;

import java.util.Map;

/**
 * Balance transaction response model.
 */
public record BalanceTransaction(
    String id,
    Integer amount,
    String currency,
    String type,
    Map<String, Object> metadata
) {
}
