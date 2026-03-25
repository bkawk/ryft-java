package com.ryft.sdk.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.ryft.sdk.core.QueryParams;
import com.ryft.sdk.core.RyftHttpClient;
import com.ryft.sdk.model.ApiList;
import com.ryft.sdk.model.Dispute;
import com.ryft.sdk.request.TimeRangePageRequest;

/**
 * Dispute read and action operations.
 */
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

  /**
   * Lists disputes using a typed time-ranged page request.
   */
  public ApiList<Dispute> listDisputes(TimeRangePageRequest pageRequest) {
    QueryParams query = QueryParams.list(
            pageRequest != null && Boolean.TRUE.equals(pageRequest.ascending()),
            pageRequest != null ? pageRequest.limit() : null,
            pageRequest != null ? pageRequest.startsAfter() : null
        )
        .put("startTimestamp", pageRequest != null ? pageRequest.startTimestamp() : null)
        .put("endTimestamp", pageRequest != null ? pageRequest.endTimestamp() : null);
    return list("disputes", query, Dispute.class);
  }

  public JsonNode get(String disputeId) {
    return getEntity("disputes/" + disputeId);
  }

  /**
   * Retrieves a dispute as a typed model.
   */
  public Dispute getDispute(String disputeId) {
    return getEntity("disputes/" + disputeId, Dispute.class);
  }

  public JsonNode accept(String disputeId) {
    return create("disputes/" + disputeId + "/accept", null);
  }

  public JsonNode challenge(String disputeId) {
    return create("disputes/" + disputeId + "/challenge", null);
  }

  /**
   * Accepts a dispute and returns the updated typed model.
   */
  public Dispute acceptDispute(String disputeId) {
    return create("disputes/" + disputeId + "/accept", null, Dispute.class);
  }

  /**
   * Challenges a dispute and returns the updated typed model.
   */
  public Dispute challengeDispute(String disputeId) {
    return create("disputes/" + disputeId + "/challenge", null, Dispute.class);
  }

  public JsonNode addEvidence(String disputeId, Object request) {
    return patch("disputes/" + disputeId + "/evidence", request);
  }

  public JsonNode deleteEvidence(String disputeId, Object request) {
    return client.delete("disputes/" + disputeId + "/evidence", request, null);
  }
}
