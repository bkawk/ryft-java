package com.ryft.sdk.request;

/**
 * Request payload to create an Apple Pay merchant session for a given domain.
 */
public record CreateApplePaySessionRequest(
    String displayName,
    String domainName
) {
}
