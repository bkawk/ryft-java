package com.ryft.sdk.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.ryft.sdk.core.QueryParams;
import com.ryft.sdk.core.RyftHttpClient;
import com.ryft.sdk.model.ApiList;

public final class TransfersService extends BaseService {
  public TransfersService(RyftHttpClient client) {
    super(client);
  }

  public JsonNode create(Object request) {
    return create("transfers", request);
  }

  public JsonNode get(String transferId) {
    return getEntity("transfers/" + transferId);
  }

  public ApiList list(Integer limit) {
    return list("transfers", new QueryParams().put("limit", limit));
  }
}
