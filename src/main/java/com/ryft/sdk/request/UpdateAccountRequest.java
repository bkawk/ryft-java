package com.ryft.sdk.request;

import java.util.Map;

public record UpdateAccountRequest(Map<String, Object> payload) {
  public static UpdateAccountRequest of(Map<String, Object> payload) {
    return new UpdateAccountRequest(payload);
  }
}
