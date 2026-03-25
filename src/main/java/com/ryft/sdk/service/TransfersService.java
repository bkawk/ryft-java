package com.ryft.sdk.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.ryft.sdk.core.QueryParams;
import com.ryft.sdk.core.RyftHttpClient;
import com.ryft.sdk.model.ApiList;
import com.ryft.sdk.model.Transfer;
import com.ryft.sdk.request.CreateTransferRequest;
import com.ryft.sdk.request.PageRequest;

/**
 * Transfer operations for moving funds between Ryft accounts.
 */
public final class TransfersService extends BaseService {
  public TransfersService(RyftHttpClient client) {
    super(client);
  }

  public JsonNode create(Object request) {
    return create("transfers", request);
  }

  public Transfer create(CreateTransferRequest request) {
    return create("transfers", request, Transfer.class);
  }

  public JsonNode get(String transferId) {
    return getEntity("transfers/" + transferId);
  }

  public Transfer getTransfer(String transferId) {
    return getEntity("transfers/" + transferId, Transfer.class);
  }

  public ApiList list(Integer limit) {
    return list("transfers", new QueryParams().put("limit", limit));
  }

  public ApiList<Transfer> listTransfers(Integer limit) {
    return list("transfers", new QueryParams().put("limit", limit), Transfer.class);
  }

  /**
   * Lists transfers using typed pagination options.
   */
  public ApiList<Transfer> listTransfers(PageRequest pageRequest) {
    QueryParams query = QueryParams.list(
        pageRequest != null ? Boolean.TRUE.equals(pageRequest.ascending()) : false,
        pageRequest != null ? pageRequest.limit() : null,
        pageRequest != null ? pageRequest.startsAfter() : null
    );
    return list("transfers", query, Transfer.class);
  }
}
