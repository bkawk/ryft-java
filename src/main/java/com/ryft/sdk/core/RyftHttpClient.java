package com.ryft.sdk.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.ryft.sdk.model.ApiError;
import com.ryft.sdk.model.ApiErrorDetail;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public final class RyftHttpClient {
  public static final String SANDBOX_BASE_URL = "https://sandbox-api.ryftpay.com/v1";
  public static final String LIVE_BASE_URL = "https://api.ryftpay.com/v1";

  private final String secretKey;
  private final String baseUrl;
  private final HttpClient httpClient;

  public RyftHttpClient(RyftConfig config) {
    String trimmedSecretKey = config.secretKey().trim();
    if (trimmedSecretKey.isEmpty()) {
      throw new IllegalArgumentException("secretKey is required");
    }
    this.secretKey = trimmedSecretKey;
    this.baseUrl = normalizeBaseUrl(config.baseUrl(), trimmedSecretKey);
    this.httpClient = Objects.requireNonNull(config.httpClient(), "httpClient is required");
  }

  public JsonNode get(String path, QueryParams query, String accountId) {
    return sendJson("GET", path, query, null, accountId);
  }

  public JsonNode post(String path, Object body, String accountId) {
    return sendJson("POST", path, null, body, accountId);
  }

  public JsonNode patch(String path, Object body, String accountId) {
    return sendJson("PATCH", path, null, body, accountId);
  }

  public JsonNode delete(String path, String accountId) {
    return sendJson("DELETE", path, null, null, accountId);
  }

  public JsonNode delete(String path, Object body, String accountId) {
    return sendJson("DELETE", path, null, body, accountId);
  }

  public JsonNode postMultipartFile(String path, Path filePath, String category, String accountId) {
    String boundary = "ryft-java-" + UUID.randomUUID();
    String fileName = filePath.getFileName().toString();

    try {
      byte[] fileBytes = Files.readAllBytes(filePath);
      String mimeType = Files.probeContentType(filePath);
      if (mimeType == null || mimeType.isBlank()) {
        mimeType = "application/octet-stream";
      }

      byte[] prefix = (
          "--" + boundary + "\r\n" +
          "Content-Disposition: form-data; name=\"file\"; filename=\"" + fileName + "\"\r\n" +
          "Content-Type: " + mimeType + "\r\n\r\n"
      ).getBytes(StandardCharsets.UTF_8);

      byte[] middle = (
          "\r\n--" + boundary + "\r\n" +
          "Content-Disposition: form-data; name=\"category\"\r\n\r\n" +
          category + "\r\n"
      ).getBytes(StandardCharsets.UTF_8);

      byte[] suffix = ("--" + boundary + "--\r\n").getBytes(StandardCharsets.UTF_8);
      byte[] payload = concat(prefix, fileBytes, middle, suffix);

      HttpRequest.Builder builder = baseRequest("POST", buildUri(path, null), accountId)
          .header("Content-Type", "multipart/form-data; boundary=" + boundary)
          .POST(BodyPublishers.ofByteArray(payload));

      return send(builder.build());
    } catch (IOException e) {
      throw new IllegalStateException("Failed to read multipart upload file", e);
    }
  }

  public String getBaseUrl() {
    return baseUrl;
  }

  private JsonNode sendJson(String method, String path, QueryParams query, Object body, String accountId) {
    HttpRequest.Builder builder = baseRequest(method, buildUri(path, query), accountId);

    try {
      if (body == null) {
        switch (method) {
          case "GET" -> builder.GET();
          case "DELETE" -> builder.DELETE();
          default -> builder.method(method, BodyPublishers.noBody());
        }
      } else {
        builder.header("Content-Type", "application/json");
        builder.method(method, BodyPublishers.ofString(Json.MAPPER.writeValueAsString(body)));
      }
    } catch (JsonProcessingException e) {
      throw new IllegalArgumentException("Failed to serialize request body", e);
    }

    return send(builder.build());
  }

  private JsonNode send(HttpRequest request) {
    try {
      HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
      String body = response.body() == null ? "" : response.body();
      if (response.statusCode() >= 400) {
        throw parseApiError(response.statusCode(), body);
      }
      if (body.isBlank()) {
        return Json.MAPPER.nullNode();
      }
      return Json.MAPPER.readTree(body);
    } catch (IOException e) {
      throw new IllegalStateException("Failed to execute Ryft request", e);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new IllegalStateException("Ryft request was interrupted", e);
    }
  }

  private HttpRequest.Builder baseRequest(String method, URI uri, String accountId) {
    HttpRequest.Builder builder = HttpRequest.newBuilder(uri)
        .timeout(Duration.ofSeconds(60))
        .header("Authorization", secretKey)
        .header("Accept", "application/json");

    if (accountId != null && !accountId.isBlank()) {
      builder.header("Account", accountId);
    }
    return builder;
  }

  private URI buildUri(String path, QueryParams query) {
    String normalizedPath = path.startsWith("/") ? path.substring(1) : path;
    String uri = baseUrl + "/" + normalizedPath;
    if (query != null && !query.isEmpty()) {
      uri += "?" + query.encode();
    }
    return URI.create(uri);
  }

  private static String normalizeBaseUrl(String configuredBaseUrl, String secretKey) {
    if (configuredBaseUrl != null && !configuredBaseUrl.isBlank()) {
      return configuredBaseUrl.strip().replaceAll("/+$", "");
    }
    if (secretKey.startsWith("sk_sandbox")) {
      return SANDBOX_BASE_URL;
    }
    if (secretKey.startsWith("sk_")) {
      return LIVE_BASE_URL;
    }
    throw new IllegalArgumentException("Invalid secret key: expected prefix 'sk_'");
  }

  private static ApiError parseApiError(int status, String body) {
    try {
      JsonNode node = body == null || body.isBlank() ? Json.MAPPER.nullNode() : Json.MAPPER.readTree(body);
      String code = node.path("code").asText(null);
      String message = node.path("message").asText(body);
      String requestId = node.path("requestId").asText(null);
      List<ApiErrorDetail> details = Json.MAPPER.convertValue(
          node.path("errors"),
          new TypeReference<>() {
          }
      );
      return new ApiError(status, code, message, requestId, details);
    } catch (Exception ignored) {
      return new ApiError(status, null, body, null, List.of());
    }
  }

  private static byte[] concat(byte[]... chunks) {
    int total = 0;
    for (byte[] chunk : chunks) {
      total += chunk.length;
    }

    byte[] merged = new byte[total];
    int offset = 0;
    for (byte[] chunk : chunks) {
      System.arraycopy(chunk, 0, merged, offset, chunk.length);
      offset += chunk.length;
    }
    return merged;
  }
}
