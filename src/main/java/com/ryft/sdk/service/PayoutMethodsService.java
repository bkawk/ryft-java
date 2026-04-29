package com.ryft.sdk.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.ryft.sdk.core.QueryParams;
import com.ryft.sdk.core.RyftHttpClient;
import com.ryft.sdk.model.ApiList;
import com.ryft.sdk.model.DeletedResource;

public final class PayoutMethodsService extends BaseService {
  public PayoutMethodsService(RyftHttpClient client) {
    super(client);
  }

  public JsonNode create(String accountId, Object request) {
    return super.create("accounts/" + accountId + "/payout-methods", request);
  }

  public JsonNode get(String accountId, String payoutMethodId) {
    return getEntity("accounts/" + accountId + "/payout-methods/" + payoutMethodId);
  }

  public ApiList list(String accountId, boolean ascending, Integer limit, String startsAfter) {
    return list("accounts/" + accountId + "/payout-methods", QueryParams.list(ascending, limit, startsAfter));
  }

  public JsonNode update(String accountId, String payoutMethodId, Object request) {
    return patch("accounts/" + accountId + "/payout-methods/" + payoutMethodId, request);
  }

  public DeletedResource delete(String accountId, String payoutMethodId) {
    return deleteResource("accounts/" + accountId + "/payout-methods/" + payoutMethodId);
  }
}
