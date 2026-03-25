package com.ryft.sdk.model;

import java.util.Map;

/**
 * Transfer response model.
 */
public record Transfer(
    String id,
    Integer amount,
    String currency,
    String destinationAccountId,
    Map<String, Object> metadata
) {
}
