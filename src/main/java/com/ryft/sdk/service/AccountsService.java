package com.ryft.sdk.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.ryft.sdk.core.RyftHttpClient;

public final class AccountsService extends BaseService {
  public AccountsService(RyftHttpClient client) {
    super(client);
  }

  public JsonNode create(Object request) {
    return create("accounts", request);
  }

  public JsonNode get(String accountId) {
    return getEntity("accounts/" + accountId);
  }

  public JsonNode update(String accountId, Object request) {
    return patch("accounts/" + accountId, request);
  }

  public JsonNode verify(String accountId) {
    return create("accounts/" + accountId + "/verify", null);
  }

  public JsonNode createAuthLink(Object request) {
    return create("accounts/authorize", request);
  }
}
