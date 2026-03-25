package com.ryft.sdk.service;

import com.ryft.sdk.core.QueryParams;
import com.ryft.sdk.core.RyftHttpClient;
import com.ryft.sdk.model.ApiList;
import com.ryft.sdk.model.BalanceTransaction;
import com.ryft.sdk.request.PageRequest;

/**
 * Balance transaction read operations for connected accounts.
 */
public final class BalanceTransactionsService extends BaseService {
  public BalanceTransactionsService(RyftHttpClient client) {
    super(client);
  }

  public ApiList list(Integer limit, String startsAfter, String payoutId, String accountId) {
    QueryParams query = new QueryParams()
        .put("limit", limit)
        .put("startsAfter", startsAfter)
        .put("payoutId", payoutId);
    return list("balance-transactions", query, accountId);
  }

  /**
   * Lists balance transactions using typed pagination options.
   */
  public ApiList<BalanceTransaction> listTransactions(PageRequest pageRequest, String payoutId, String accountId) {
    QueryParams query = new QueryParams()
        .put("limit", pageRequest != null ? pageRequest.limit() : null)
        .put("startsAfter", pageRequest != null ? pageRequest.startsAfter() : null)
        .put("payoutId", payoutId);
    return list("balance-transactions", query, accountId, BalanceTransaction.class);
  }
}
