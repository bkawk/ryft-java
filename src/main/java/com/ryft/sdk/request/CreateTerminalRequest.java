package com.ryft.sdk.request;

import com.ryft.sdk.model.Metadata;

/**
 * Request payload to register a new in-person terminal.
 */
public record CreateTerminalRequest(
    String serialNumber,
    String locationId,
    String name,
    Metadata metadata
) {
}
