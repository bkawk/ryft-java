package com.ryft.sdk.request;

import java.util.List;

/**
 * Split payment allocation request.
 */
public record PaymentSessionSplitRequest(List<PaymentSessionSplitItemRequest> items) {
  public static PaymentSessionSplitRequest of(List<PaymentSessionSplitItemRequest> items) {
    return new PaymentSessionSplitRequest(items);
  }
}
