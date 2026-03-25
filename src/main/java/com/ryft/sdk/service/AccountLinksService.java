package com.ryft.sdk.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.ryft.sdk.core.RyftHttpClient;

public final class AccountLinksService extends BaseService {
  public AccountLinksService(RyftHttpClient client) {
    super(client);
  }

  public JsonNode generateTemporaryAccountLink(Object request) {
    return create("account-links", request);
  }
}
