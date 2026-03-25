package com.ryft.sdk.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.ryft.sdk.core.QueryParams;
import com.ryft.sdk.core.RyftHttpClient;
import com.ryft.sdk.model.ApiList;

public final class PlatformFeesService extends BaseService {
  public PlatformFeesService(RyftHttpClient client) {
    super(client);
  }

  public ApiList list(boolean ascending, Integer limit) {
    QueryParams query = new QueryParams()
        .putBoolean("ascending", ascending)
        .put("limit", limit);
    return list("platform-fees", query);
  }

  public JsonNode get(String feeId) {
    return getEntity("platform-fees/" + feeId);
  }

  public ApiList getRefunds(String feeId) {
    return list("platform-fees/" + feeId + "/refunds", null);
  }
}
