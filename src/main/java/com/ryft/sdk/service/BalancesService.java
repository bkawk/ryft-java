package com.ryft.sdk.service;

import com.ryft.sdk.core.QueryParams;
import com.ryft.sdk.core.RyftHttpClient;
import com.ryft.sdk.model.ApiList;

public final class BalancesService extends BaseService {
  public BalancesService(RyftHttpClient client) {
    super(client);
  }

  public ApiList list(String currency, String accountId) {
    QueryParams query = new QueryParams().put("currency", currency);
    return list("balances", query, accountId);
  }
}
