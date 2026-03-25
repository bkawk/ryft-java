package com.ryft.sdk.request;

import com.ryft.sdk.model.BillingAddress;

public record UpdatePaymentMethodRequest(BillingAddress billingAddress) {
  public static UpdatePaymentMethodRequest of(BillingAddress billingAddress) {
    return new UpdatePaymentMethodRequest(billingAddress);
  }
}
