package com.ryft.sdk.core;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;

public final class QueryParams {
  private final Map<String, String> values = new LinkedHashMap<>();

  public QueryParams put(String key, Object value) {
    if (value == null) {
      return this;
    }
    String stringValue = Objects.toString(value, "").trim();
    if (!stringValue.isEmpty()) {
      values.put(key, stringValue);
    }
    return this;
  }

  public QueryParams putBoolean(String key, boolean value) {
    values.put(key, Boolean.toString(value));
    return this;
  }

  public boolean isEmpty() {
    return values.isEmpty();
  }

  public String encode() {
    StringJoiner joiner = new StringJoiner("&");
    values.forEach((key, value) -> joiner.add(encodeComponent(key) + "=" + encodeComponent(value)));
    return joiner.toString();
  }

  public static QueryParams list(boolean ascending, Integer limit, String startsAfter) {
    return new QueryParams()
        .putBoolean("ascending", ascending)
        .put("limit", limit)
        .put("startsAfter", startsAfter);
  }

  private static String encodeComponent(String value) {
    return URLEncoder.encode(value, StandardCharsets.UTF_8);
  }
}
