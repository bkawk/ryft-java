package com.ryft.sdk.request;

import com.ryft.sdk.model.Metadata;

/**
 * Request payload to update an existing in-person terminal.
 */
public record UpdateTerminalRequest(
    String locationId,
    String name,
    Metadata metadata
) {
}
