package com.ryft.sdk.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.ryft.sdk.core.RyftHttpClient;
import com.ryft.sdk.model.Account;
import com.ryft.sdk.model.AccountAuthorizationLink;
import com.ryft.sdk.request.CreateAccountAuthorizationRequest;
import com.ryft.sdk.request.CreateAccountRequest;
import com.ryft.sdk.request.UpdateAccountRequest;

public final class AccountsService extends BaseService {
  public AccountsService(RyftHttpClient client) {
    super(client);
  }

  public JsonNode create(Object request) {
    return create("accounts", request);
  }

  public Account create(CreateAccountRequest request) {
    return create("accounts", request, Account.class);
  }

  public JsonNode get(String accountId) {
    return getEntity("accounts/" + accountId);
  }

  public Account getAccount(String accountId) {
    return getEntity("accounts/" + accountId, Account.class);
  }

  public JsonNode update(String accountId, Object request) {
    return patch("accounts/" + accountId, request);
  }

  public Account update(String accountId, UpdateAccountRequest request) {
    return patch("accounts/" + accountId, request.payload(), Account.class);
  }

  public JsonNode verify(String accountId) {
    return create("accounts/" + accountId + "/verify", null);
  }

  public Account verifyAccount(String accountId) {
    return create("accounts/" + accountId + "/verify", null, Account.class);
  }

  public JsonNode createAuthLink(Object request) {
    return create("accounts/authorize", request);
  }

  public AccountAuthorizationLink createAuthLink(CreateAccountAuthorizationRequest request) {
    return create("accounts/authorize", request, AccountAuthorizationLink.class);
  }
}
