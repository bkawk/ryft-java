package com.ryft.examples.httpjdk;

import com.ryft.sdk.RyftClient;
import com.ryft.sdk.core.Json;
import com.ryft.sdk.model.ApiError;
import com.ryft.sdk.model.Metadata;
import com.ryft.sdk.request.CreateCustomerRequest;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public final class HttpServerExample {
  private HttpServerExample() {
  }

  public static void main(String[] args) throws IOException {
    String secretKey = System.getenv("RYFT_SECRET_KEY");
    if (secretKey == null || secretKey.isBlank()) {
      throw new IllegalStateException("Set RYFT_SECRET_KEY before running the example");
    }

    RyftClient client = new RyftClient(secretKey);
    HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
    server.createContext("/customers", exchange -> handleCustomerCreate(exchange, client));
    server.start();

    System.out.println("Listening on http://localhost:8080");
  }

  private static void handleCustomerCreate(HttpExchange exchange, RyftClient client) throws IOException {
    if (!"POST".equalsIgnoreCase(exchange.getRequestMethod())) {
      writeJson(exchange, 405, Map.of("message", "Method not allowed"));
      return;
    }

    try {
      @SuppressWarnings("unchecked")
      Map<String, Object> request = Json.MAPPER.readValue(exchange.getRequestBody(), Map.class);
      var customer = client.customers().create(
          CreateCustomerRequest.builder(String.valueOf(request.getOrDefault("email", "")))
              .firstName(stringValue(request.get("firstName")))
              .lastName(stringValue(request.get("lastName")))
              .metadata(metadataValue(request.get("metadata")))
              .build()
      );
      writeJson(exchange, 200, customer);
    } catch (ApiError error) {
      writeJson(exchange, error.getStatus(), Map.of(
          "code", error.getCode(),
          "message", error.getMessage(),
          "requestId", error.getRequestId(),
          "errors", error.getErrors()
      ));
    } catch (Exception error) {
      writeJson(exchange, 500, Map.of("message", error.getMessage()));
    }
  }

  private static void writeJson(HttpExchange exchange, int status, Object payload) throws IOException {
    byte[] body = Json.MAPPER.writeValueAsString(payload).getBytes(StandardCharsets.UTF_8);
    exchange.getResponseHeaders().add("Content-Type", "application/json");
    exchange.sendResponseHeaders(status, body.length);
    try (OutputStream output = exchange.getResponseBody()) {
      output.write(body);
    }
  }

  @SuppressWarnings("unchecked")
  private static Metadata metadataValue(Object value) {
    if (value instanceof Map<?, ?> rawMap) {
      return Metadata.of((Map<String, Object>) rawMap);
    }
    return null;
  }

  private static String stringValue(Object value) {
    return value == null ? null : String.valueOf(value);
  }
}
