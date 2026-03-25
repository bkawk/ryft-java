package com.ryft.sdk.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.ryft.sdk.core.QueryParams;
import com.ryft.sdk.core.RyftHttpClient;
import com.ryft.sdk.model.ApiList;
import com.ryft.sdk.model.DeletedResource;

public final class CustomersService extends BaseService {
  public CustomersService(RyftHttpClient client) {
    super(client);
  }

  public JsonNode create(Object request) {
    return create("customers", request);
  }

  public ApiList list(String email, Integer startTimestamp, Integer endTimestamp, boolean ascending, Integer limit, String startsAfter) {
    QueryParams query = QueryParams.list(ascending, limit, startsAfter)
        .put("email", email)
        .put("startTimestamp", startTimestamp)
        .put("endTimestamp", endTimestamp);
    return list("customers", query);
  }

  public JsonNode get(String customerId) {
    return getEntity("customers/" + customerId);
  }

  public JsonNode update(String customerId, Object request) {
    return patch("customers/" + customerId, request);
  }

  public DeletedResource delete(String customerId) {
    return deleteResource("customers/" + customerId);
  }

  public ApiList getPaymentMethods(String customerId) {
    return list("customers/" + customerId + "/payment-methods", null);
  }
}
