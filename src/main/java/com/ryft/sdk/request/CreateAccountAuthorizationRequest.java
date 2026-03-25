package com.ryft.sdk.request;

public record CreateAccountAuthorizationRequest(String email, String redirectUrl) {
  public static CreateAccountAuthorizationRequest of(String email, String redirectUrl) {
    return new CreateAccountAuthorizationRequest(email, redirectUrl);
  }
}
