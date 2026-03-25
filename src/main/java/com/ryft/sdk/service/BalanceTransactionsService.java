package com.ryft.sdk.service;

import com.ryft.sdk.core.QueryParams;
import com.ryft.sdk.core.RyftHttpClient;
import com.ryft.sdk.model.ApiList;

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
}
