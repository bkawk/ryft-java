package com.ryft.sdk.request;

import com.ryft.sdk.model.Metadata;

/**
 * Request payload to update an existing in-person location.
 */
public record UpdateInPersonLocationRequest(
    String name,
    Metadata metadata
) {
}
