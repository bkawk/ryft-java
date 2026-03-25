package com.ryft.sdk.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.ryft.sdk.core.QueryParams;
import com.ryft.sdk.core.RyftHttpClient;
import com.ryft.sdk.model.ApiList;

public final class DisputesService extends BaseService {
  public DisputesService(RyftHttpClient client) {
    super(client);
  }

  public ApiList list(Integer startTimestamp, Integer endTimestamp, boolean ascending, Integer limit, String startsAfter) {
    QueryParams query = QueryParams.list(ascending, limit, startsAfter)
        .put("startTimestamp", startTimestamp)
        .put("endTimestamp", endTimestamp);
    return list("disputes", query);
  }

  public JsonNode get(String disputeId) {
    return getEntity("disputes/" + disputeId);
  }

  public JsonNode accept(String disputeId) {
    return create("disputes/" + disputeId + "/accept", null);
  }

  public JsonNode challenge(String disputeId) {
    return create("disputes/" + disputeId + "/challenge", null);
  }

  public JsonNode addEvidence(String disputeId, Object request) {
    return patch("disputes/" + disputeId + "/evidence", request);
  }

  public JsonNode deleteEvidence(String disputeId, Object request) {
    return client.delete("disputes/" + disputeId + "/evidence", request, null);
  }
}
