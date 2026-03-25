package com.ryft.sdk.testsupport;

import java.io.ByteArrayOutputStream;
import java.net.CookieHandler;
import java.net.ProxySelector;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Flow;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public final class FakeHttpClient extends HttpClient {
  @FunctionalInterface
  public interface Responder {
    HttpResponse<String> respond(HttpRequest request);
  }

  private final Responder responder;
  private HttpRequest lastRequest;
  private String lastBody = "";

  public FakeHttpClient(Responder responder) {
    this.responder = responder;
  }

  public HttpRequest getLastRequest() {
    return lastRequest;
  }

  public String getLastBody() {
    return lastBody;
  }

  @Override
  public Optional<CookieHandler> cookieHandler() {
    return Optional.empty();
  }

  @Override
  public Optional<Duration> connectTimeout() {
    return Optional.of(Duration.ofSeconds(30));
  }

  @Override
  public Redirect followRedirects() {
    return Redirect.NEVER;
  }

  @Override
  public Optional<ProxySelector> proxy() {
    return Optional.empty();
  }

  @Override
  public SSLContext sslContext() {
    try {
      SSLContext context = SSLContext.getInstance("TLS");
      context.init(null, new TrustManager[]{new X509TrustManager() {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
          return new X509Certificate[0];
        }
      }}, new SecureRandom());
      return context;
    } catch (Exception error) {
      throw new IllegalStateException("Unable to create SSL context for fake client", error);
    }
  }

  @Override
  public SSLParameters sslParameters() {
    return new SSLParameters();
  }

  @Override
  public Optional<java.net.Authenticator> authenticator() {
    return Optional.empty();
  }

  @Override
  public Version version() {
    return Version.HTTP_1_1;
  }

  @Override
  public Optional<Executor> executor() {
    return Optional.empty();
  }

  @Override
  public <T> HttpResponse<T> send(HttpRequest request, HttpResponse.BodyHandler<T> responseBodyHandler) {
    lastRequest = request;
    lastBody = readBody(request);
    @SuppressWarnings("unchecked")
    HttpResponse<T> response = (HttpResponse<T>) responder.respond(request);
    return response;
  }

  @Override
  public <T> CompletableFuture<HttpResponse<T>> sendAsync(HttpRequest request, HttpResponse.BodyHandler<T> responseBodyHandler) {
    return CompletableFuture.completedFuture(send(request, responseBodyHandler));
  }

  @Override
  public <T> CompletableFuture<HttpResponse<T>> sendAsync(
      HttpRequest request,
      HttpResponse.BodyHandler<T> responseBodyHandler,
      HttpResponse.PushPromiseHandler<T> pushPromiseHandler
  ) {
    return CompletableFuture.completedFuture(send(request, responseBodyHandler));
  }

  public static HttpResponse<String> jsonResponse(int statusCode, String body) {
    return new HttpResponse<>() {
      @Override
      public int statusCode() {
        return statusCode;
      }

      @Override
      public HttpRequest request() {
        return null;
      }

      @Override
      public Optional<HttpResponse<String>> previousResponse() {
        return Optional.empty();
      }

      @Override
      public HttpHeaders headers() {
        return HttpHeaders.of(java.util.Map.of("Content-Type", List.of("application/json")), (a, b) -> true);
      }

      @Override
      public String body() {
        return body;
      }

      @Override
      public Optional<SSLSession> sslSession() {
        return Optional.empty();
      }

      @Override
      public URI uri() {
        return URI.create("https://example.test");
      }

      @Override
      public Version version() {
        return Version.HTTP_1_1;
      }
    };
  }

  private static String readBody(HttpRequest request) {
    return request.bodyPublisher().map(FakeHttpClient::readBody).orElse("");
  }

  private static String readBody(HttpRequest.BodyPublisher publisher) {
    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    publisher.subscribe(new Flow.Subscriber<>() {
      private Flow.Subscription subscription;

      @Override
      public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        subscription.request(Long.MAX_VALUE);
      }

      @Override
      public void onNext(ByteBuffer item) {
        byte[] chunk = new byte[item.remaining()];
        item.get(chunk);
        buffer.writeBytes(chunk);
      }

      @Override
      public void onError(Throwable throwable) {
        if (subscription != null) {
          subscription.cancel();
        }
        throw new IllegalStateException("Failed to read HTTP request body", throwable);
      }

      @Override
      public void onComplete() {
      }
    });
    return buffer.toString(java.nio.charset.StandardCharsets.UTF_8);
  }
}
