package com.ryft.sdk.request;

/**
 * Terms of service wrapper for connected account onboarding.
 */
public record TermsOfService(AccountAcceptance acceptance) {
  public static TermsOfService of(AccountAcceptance acceptance) {
    return new TermsOfService(acceptance);
  }
}
