package com.ryft.sdk.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.ryft.sdk.core.QueryParams;
import com.ryft.sdk.core.RyftHttpClient;
import com.ryft.sdk.model.ApiList;
import com.ryft.sdk.model.Transfer;
import com.ryft.sdk.request.CreateTransferRequest;

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
}
