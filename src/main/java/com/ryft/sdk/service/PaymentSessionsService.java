package com.ryft.sdk.service;

import com.ryft.sdk.core.QueryParams;
import com.ryft.sdk.core.RyftHttpClient;
import com.ryft.sdk.model.ApiList;
import com.ryft.sdk.model.PaymentSession;
import com.ryft.sdk.model.PaymentSessionTransaction;
import com.ryft.sdk.request.CreatePaymentSessionRequest;
import com.ryft.sdk.request.RefundPaymentSessionRequest;
import com.ryft.sdk.request.UpdatePaymentSessionRequest;

public final class PaymentSessionsService extends BaseService {
  public PaymentSessionsService(RyftHttpClient client) {
    super(client);
  }

  public PaymentSession create(Object request) {
    return create("payment-sessions", request, PaymentSession.class);
  }

  public PaymentSession create(CreatePaymentSessionRequest request) {
    return create("payment-sessions", request, PaymentSession.class);
  }

  public PaymentSession createForAccount(Object request, String accountId) {
    return create("payment-sessions", request, accountId, PaymentSession.class);
  }

  public PaymentSession createForAccount(CreatePaymentSessionRequest request, String accountId) {
    return create("payment-sessions", request, accountId, PaymentSession.class);
  }

  public PaymentSession get(String paymentSessionId) {
    return getEntity("payment-sessions/" + paymentSessionId, PaymentSession.class);
  }

  public PaymentSession getForAccount(String paymentSessionId, String accountId) {
    return getEntity("payment-sessions/" + paymentSessionId, accountId, PaymentSession.class);
  }

  public PaymentSession update(String paymentSessionId, Object request) {
    return patch("payment-sessions/" + paymentSessionId, request, PaymentSession.class);
  }

  public PaymentSession update(String paymentSessionId, UpdatePaymentSessionRequest request) {
    return patch("payment-sessions/" + paymentSessionId, request, PaymentSession.class);
  }

  public PaymentSession updateForAccount(String paymentSessionId, Object request, String accountId) {
    return patch("payment-sessions/" + paymentSessionId, request, accountId, PaymentSession.class);
  }

  public PaymentSession updateForAccount(String paymentSessionId, UpdatePaymentSessionRequest request, String accountId) {
    return patch("payment-sessions/" + paymentSessionId, request, accountId, PaymentSession.class);
  }

  public PaymentSessionTransaction refund(String paymentSessionId, Object request) {
    return create("payment-sessions/" + paymentSessionId + "/refunds", request, PaymentSessionTransaction.class);
  }

  public PaymentSessionTransaction refund(String paymentSessionId, RefundPaymentSessionRequest request) {
    return create("payment-sessions/" + paymentSessionId + "/refunds", request, PaymentSessionTransaction.class);
  }

  public PaymentSessionTransaction refundForAccount(String paymentSessionId, Object request, String accountId) {
    return create("payment-sessions/" + paymentSessionId + "/refunds", request, accountId, PaymentSessionTransaction.class);
  }

  public PaymentSessionTransaction refundForAccount(String paymentSessionId, RefundPaymentSessionRequest request, String accountId) {
    return create("payment-sessions/" + paymentSessionId + "/refunds", request, accountId, PaymentSessionTransaction.class);
  }

  public ApiList<PaymentSessionTransaction> listTransactions(String paymentSessionId, Integer startTimestamp, Integer endTimestamp, boolean ascending, Integer limit) {
    QueryParams query = new QueryParams()
        .put("startTimestamp", startTimestamp)
        .put("endTimestamp", endTimestamp)
        .putBoolean("ascending", ascending)
        .put("limit", limit);
    return list("payment-sessions/" + paymentSessionId + "/transactions", query, PaymentSessionTransaction.class);
  }

  public ApiList<PaymentSessionTransaction> listTransactionsForAccount(
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
    return list("payment-sessions/" + paymentSessionId + "/transactions", query, accountId, PaymentSessionTransaction.class);
  }

  public PaymentSessionTransaction getTransaction(String paymentSessionId, String transactionId) {
    return getEntity("payment-sessions/" + paymentSessionId + "/transactions/" + transactionId, PaymentSessionTransaction.class);
  }

  public PaymentSessionTransaction getTransactionForAccount(String paymentSessionId, String transactionId, String accountId) {
    return getEntity("payment-sessions/" + paymentSessionId + "/transactions/" + transactionId, accountId, PaymentSessionTransaction.class);
  }
}
