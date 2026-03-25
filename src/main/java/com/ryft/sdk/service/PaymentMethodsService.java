package com.ryft.sdk.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.ryft.sdk.core.RyftHttpClient;
import com.ryft.sdk.model.DeletedResource;

public final class PaymentMethodsService extends BaseService {
  public PaymentMethodsService(RyftHttpClient client) {
    super(client);
  }

  public JsonNode get(String paymentMethodId) {
    return getEntity("payment-methods/" + paymentMethodId);
  }

  public JsonNode update(String paymentMethodId, Object request) {
    return patch("payment-methods/" + paymentMethodId, request);
  }

  public DeletedResource delete(String paymentMethodId) {
    return deleteResource("payment-methods/" + paymentMethodId);
  }
}
