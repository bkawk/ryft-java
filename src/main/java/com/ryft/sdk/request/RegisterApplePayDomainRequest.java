package com.ryft.sdk.request;

/**
 * Request payload to register an Apple Pay merchant web domain.
 */
public record RegisterApplePayDomainRequest(
    String domainName
) {
}
