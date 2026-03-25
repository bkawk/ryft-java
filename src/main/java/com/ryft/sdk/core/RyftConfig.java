package com.ryft.sdk.core;

import java.net.http.HttpClient;
import java.time.Duration;
import java.util.Objects;

public record RyftConfig(String secretKey, String baseUrl, HttpClient httpClient) {
  public RyftConfig {
    Objects.requireNonNull(secretKey, "secretKey is required");
  }

  public static Builder builder(String secretKey) {
    return new Builder(secretKey);
  }

  public static final class Builder {
    private final String secretKey;
    private String baseUrl;
    private HttpClient httpClient;

    private Builder(String secretKey) {
      this.secretKey = secretKey;
    }

    public Builder baseUrl(String baseUrl) {
      this.baseUrl = baseUrl;
      return this;
    }

    public Builder httpClient(HttpClient httpClient) {
      this.httpClient = httpClient;
      return this;
    }

    public RyftConfig build() {
      HttpClient effectiveClient = httpClient;
      if (effectiveClient == null) {
        effectiveClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(30))
            .build();
      }
      return new RyftConfig(secretKey, baseUrl, effectiveClient);
    }
  }
}
