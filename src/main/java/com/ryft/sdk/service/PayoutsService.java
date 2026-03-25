package com.ryft.sdk.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.ryft.sdk.core.QueryParams;
import com.ryft.sdk.core.RyftHttpClient;
import com.ryft.sdk.model.ApiList;

public final class PayoutsService extends BaseService {
  public PayoutsService(RyftHttpClient client) {
    super(client);
  }

  public JsonNode create(String accountId, Object request) {
    return create("accounts/" + accountId + "/payouts", request);
  }

  public JsonNode get(String accountId, String payoutId) {
    return getEntity("accounts/" + accountId + "/payouts/" + payoutId);
  }

  public ApiList list(String accountId, Integer startTimestamp, Integer endTimestamp, boolean ascending, Integer limit, String startsAfter) {
    QueryParams query = QueryParams.list(ascending, limit, startsAfter)
        .put("startTimestamp", startTimestamp)
        .put("endTimestamp", endTimestamp);
    return list("accounts/" + accountId + "/payouts", query);
  }
}
