package com.ryft.sdk.request;

/**
 * Fee allocation payload for split payments.
 */
public record FeeRequest(Integer amount) {
  /**
   * Creates a fee payload with the provided amount.
   */
  public static FeeRequest of(Integer amount) {
    return new FeeRequest(amount);
  }
}
