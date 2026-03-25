package com.ryft.sdk.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.ryft.sdk.core.QueryParams;
import com.ryft.sdk.core.RyftHttpClient;
import com.ryft.sdk.model.ApiList;
import com.ryft.sdk.model.PlatformFee;
import com.ryft.sdk.model.PlatformFeeRefund;
import com.ryft.sdk.request.PageRequest;

/**
 * Platform fee read operations.
 */
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

  /**
   * Lists platform fees using typed pagination options.
   */
  public ApiList<PlatformFee> listPlatformFees(PageRequest pageRequest) {
    QueryParams query = new QueryParams()
        .putBoolean("ascending", pageRequest != null && Boolean.TRUE.equals(pageRequest.ascending()))
        .put("limit", pageRequest != null ? pageRequest.limit() : null)
        .put("startsAfter", pageRequest != null ? pageRequest.startsAfter() : null);
    return list("platform-fees", query, PlatformFee.class);
  }

  public JsonNode get(String feeId) {
    return getEntity("platform-fees/" + feeId);
  }

  /**
   * Retrieves a platform fee as a typed model.
   */
  public PlatformFee getPlatformFee(String feeId) {
    return getEntity("platform-fees/" + feeId, PlatformFee.class);
  }

  public ApiList getRefunds(String feeId) {
    return list("platform-fees/" + feeId + "/refunds", null);
  }

  /**
   * Lists refunds for a platform fee as typed models.
   */
  public ApiList<PlatformFeeRefund> listRefunds(String feeId) {
    return list("platform-fees/" + feeId + "/refunds", null, PlatformFeeRefund.class);
  }
}
