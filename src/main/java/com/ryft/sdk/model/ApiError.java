package com.ryft.sdk.model;

import java.util.List;

public class ApiError extends RuntimeException {
  private final int status;
  private final String code;
  private final String requestId;
  private final List<ApiErrorDetail> errors;

  public ApiError(int status, String code, String message, String requestId, List<ApiErrorDetail> errors) {
    super(message == null || message.isBlank() ? (code == null || code.isBlank() ? "Ryft API error" : code) : message);
    this.status = status;
    this.code = code;
    this.requestId = requestId;
    this.errors = errors == null ? List.of() : List.copyOf(errors);
  }

  public int getStatus() {
    return status;
  }

  public String getCode() {
    return code;
  }

  public String getRequestId() {
    return requestId;
  }

  public List<ApiErrorDetail> getErrors() {
    return errors;
  }
}
