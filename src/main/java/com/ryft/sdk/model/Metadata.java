package com.ryft.sdk.model;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Small typed wrapper for resource metadata values.
 */
public final class Metadata extends LinkedHashMap<String, Object> {
  public Metadata() {
    super();
  }

  public Metadata(Map<String, Object> values) {
    super(values == null ? Map.of() : values);
  }

  public static Metadata of(Map<String, Object> values) {
    return values == null ? null : new Metadata(values);
  }

  public static Metadata of(String key, Object value) {
    Metadata metadata = new Metadata();
    metadata.put(key, value);
    return metadata;
  }

  /**
   * Adds a metadata entry and returns the same instance for fluent usage.
   */
  public Metadata with(String key, Object value) {
    put(key, value);
    return this;
  }
}
