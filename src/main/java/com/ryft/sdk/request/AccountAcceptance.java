package com.ryft.sdk.request;

/**
 * Terms acceptance details for connected account onboarding.
 */
public record AccountAcceptance(String ipAddress) {
  public static AccountAcceptance of(String ipAddress) {
    return new AccountAcceptance(ipAddress);
  }
}
