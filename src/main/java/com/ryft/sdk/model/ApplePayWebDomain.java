package com.ryft.sdk.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Apple Pay web domain registration response.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record ApplePayWebDomain(
    String id,
    String domainName,
    Long createdTimestamp
) {
}
