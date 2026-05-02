package com.ryft.sdk.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Apple Pay web session response.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record ApplePayWebSession(
    String sessionObject
) {
}
