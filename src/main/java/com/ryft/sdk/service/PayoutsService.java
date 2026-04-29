package com.ryft.sdk.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.ryft.sdk.core.QueryParams;
import com.ryft.sdk.core.RyftHttpClient;
import com.ryft.sdk.model.ApiList;
import com.ryft.sdk.model.Payout;
import com.ryft.sdk.request.CreatePayoutRequest;
import com.ryft.sdk.request.TimeRangePageRequest;

/**
 * Payout operations for connected Ryft accounts.
 */
public final class PayoutsService extends BaseService {
  public PayoutsService(RyftHttpClient client) {
    super(client);
  }

  public JsonNode create(String accountId, Object request) {
    return super.create("accounts/" + accountId + "/payouts", request);
  }

  public Payout create(String accountId, CreatePayoutRequest request) {
    return create("accounts/" + accountId + "/payouts", request, Payout.class);
  }

  public JsonNode get(String accountId, String payoutId) {
    return getEntity("accounts/" + accountId + "/payouts/" + payoutId);
  }

  public Payout getPayout(String accountId, String payoutId) {
    return getEntity("accounts/" + accountId + "/payouts/" + payoutId, Payout.class);
  }

  public ApiList list(String accountId, Integer startTimestamp, Integer endTimestamp, boolean ascending, Integer limit, String startsAfter) {
    QueryParams query = QueryParams.list(ascending, limit, startsAfter)
        .put("startTimestamp", startTimestamp)
        .put("endTimestamp", endTimestamp);
    return list("accounts/" + accountId + "/payouts", query);
  }

  public ApiList<Payout> listPayouts(String accountId, Integer startTimestamp, Integer endTimestamp, boolean ascending, Integer limit, String startsAfter) {
    QueryParams query = QueryParams.list(ascending, limit, startsAfter)
        .put("startTimestamp", startTimestamp)
        .put("endTimestamp", endTimestamp);
    return list("accounts/" + accountId + "/payouts", query, Payout.class);
  }

  /**
   * Lists payouts using a typed time-ranged page request.
   */
  public ApiList<Payout> listPayouts(String accountId, TimeRangePageRequest pageRequest) {
    QueryParams query = QueryParams.list(
            pageRequest != null ? Boolean.TRUE.equals(pageRequest.ascending()) : false,
            pageRequest != null ? pageRequest.limit() : null,
            pageRequest != null ? pageRequest.startsAfter() : null
        )
        .put("startTimestamp", pageRequest != null ? pageRequest.startTimestamp() : null)
        .put("endTimestamp", pageRequest != null ? pageRequest.endTimestamp() : null);
    return list("accounts/" + accountId + "/payouts", query, Payout.class);
  }
}
