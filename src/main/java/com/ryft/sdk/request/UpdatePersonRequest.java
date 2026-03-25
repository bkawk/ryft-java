package com.ryft.sdk.request;

import java.util.Map;

public record UpdatePersonRequest(Map<String, Object> payload) {
  public static UpdatePersonRequest of(Map<String, Object> payload) {
    return new UpdatePersonRequest(payload);
  }
}
