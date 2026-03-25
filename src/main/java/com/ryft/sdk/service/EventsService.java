package com.ryft.sdk.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.ryft.sdk.core.QueryParams;
import com.ryft.sdk.core.RyftHttpClient;
import com.ryft.sdk.model.ApiList;

public final class EventsService extends BaseService {
  public EventsService(RyftHttpClient client) {
    super(client);
  }

  public ApiList list(boolean ascending, Integer limit, String accountId) {
    QueryParams query = new QueryParams()
        .putBoolean("ascending", ascending)
        .put("limit", limit);
    return list("events", query, accountId);
  }

  public JsonNode get(String eventId, String accountId) {
    return getEntity("events/" + eventId, accountId);
  }
}
