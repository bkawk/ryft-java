package com.ryft.sdk.service;

import com.ryft.sdk.core.Json;
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

  public ApiList<Dispute> list(Integer startTimestamp, Integer endTimestamp, boolean ascending, Integer limit, String startsAfter) {
    QueryParams query = QueryParams.list(ascending, limit, startsAfter)
        .put("startTimestamp", startTimestamp)
        .put("endTimestamp", endTimestamp);
    return list("disputes", query, Dispute.class);
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

  public Dispute get(String disputeId) {
    return getEntity("disputes/" + disputeId, Dispute.class);
  }

  public Dispute accept(String disputeId) {
    return create("disputes/" + disputeId + "/accept", null, Dispute.class);
  }

  public Dispute challenge(String disputeId) {
    return create("disputes/" + disputeId + "/challenge", null, Dispute.class);
  }

  public Dispute addEvidence(String disputeId, Object request) {
    return patch("disputes/" + disputeId + "/evidence", request, Dispute.class);
  }

  public Dispute deleteEvidence(String disputeId, Object request) {
    return Json.MAPPER.convertValue(client.delete("disputes/" + disputeId + "/evidence", request, null), Dispute.class);
  }
}
