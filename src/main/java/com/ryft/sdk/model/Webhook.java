package com.ryft.sdk.model;

import java.util.List;

public record Webhook(
    String id,
    String url,
    Boolean active,
    List<String> eventTypes
) {
}
