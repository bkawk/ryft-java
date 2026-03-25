package com.ryft.sdk.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.ryft.sdk.core.QueryParams;
import com.ryft.sdk.core.RyftHttpClient;
import com.ryft.sdk.model.ApiList;

public final class PaymentSessionsService extends BaseService {
  public PaymentSessionsService(RyftHttpClient client) {
    super(client);
  }

  public JsonNode create(Object request) {
    return create("payment-sessions", request);
  }

  public JsonNode createForAccount(Object request, String accountId) {
    return create("payment-sessions", request, accountId);
  }

  public JsonNode get(String paymentSessionId) {
    return getEntity("payment-sessions/" + paymentSessionId);
  }

  public JsonNode getForAccount(String paymentSessionId, String accountId) {
    return getEntity("payment-sessions/" + paymentSessionId, accountId);
  }

  public JsonNode update(String paymentSessionId, Object request) {
    return patch("payment-sessions/" + paymentSessionId, request);
  }

  public JsonNode updateForAccount(String paymentSessionId, Object request, String accountId) {
    return patch("payment-sessions/" + paymentSessionId, request, accountId);
  }

  public JsonNode refund(String paymentSessionId, Object request) {
    return create("payment-sessions/" + paymentSessionId + "/refunds", request);
  }

  public JsonNode refundForAccount(String paymentSessionId, Object request, String accountId) {
    return create("payment-sessions/" + paymentSessionId + "/refunds", request, accountId);
  }

  public ApiList listTransactions(String paymentSessionId, Integer startTimestamp, Integer endTimestamp, boolean ascending, Integer limit) {
    QueryParams query = new QueryParams()
        .put("startTimestamp", startTimestamp)
        .put("endTimestamp", endTimestamp)
        .putBoolean("ascending", ascending)
        .put("limit", limit);
    return list("payment-sessions/" + paymentSessionId + "/transactions", query);
  }

  public ApiList listTransactionsForAccount(
      String paymentSessionId,
      Integer startTimestamp,
      Integer endTimestamp,
      boolean ascending,
      Integer limit,
      String accountId
  ) {
    QueryParams query = new QueryParams()
        .put("startTimestamp", startTimestamp)
        .put("endTimestamp", endTimestamp)
        .putBoolean("ascending", ascending)
        .put("limit", limit);
    return list("payment-sessions/" + paymentSessionId + "/transactions", query, accountId);
  }

  public JsonNode getTransaction(String paymentSessionId, String transactionId) {
    return getEntity("payment-sessions/" + paymentSessionId + "/transactions/" + transactionId);
  }

  public JsonNode getTransactionForAccount(String paymentSessionId, String transactionId, String accountId) {
    return getEntity("payment-sessions/" + paymentSessionId + "/transactions/" + transactionId, accountId);
  }
}
