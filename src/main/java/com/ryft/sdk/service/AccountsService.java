package com.ryft.sdk.service;

import com.ryft.sdk.core.RyftHttpClient;
import com.ryft.sdk.model.Account;
import com.ryft.sdk.model.AccountAuthorizationLink;
import com.ryft.sdk.request.CreateAccountAuthorizationRequest;
import com.ryft.sdk.request.CreateAccountRequest;
import com.ryft.sdk.request.UpdateAccountRequest;

/**
 * Connected account operations for the Ryft API.
 */
public final class AccountsService extends BaseService {
  public AccountsService(RyftHttpClient client) {
    super(client);
  }

  public Account create(Object request) {
    return create("accounts", request, Account.class);
  }

  public Account create(CreateAccountRequest request) {
    return create("accounts", request, Account.class);
  }

  public Account get(String accountId) {
    return getEntity("accounts/" + accountId, Account.class);
  }

  public Account update(String accountId, Object request) {
    return patch("accounts/" + accountId, request, Account.class);
  }

  public Account update(String accountId, UpdateAccountRequest request) {
    return patch("accounts/" + accountId, request, Account.class);
  }

  public Account verify(String accountId) {
    return create("accounts/" + accountId + "/verify", null, Account.class);
  }

  public AccountAuthorizationLink createAuthLink(Object request) {
    return create("accounts/authorize", request, AccountAuthorizationLink.class);
  }

  public AccountAuthorizationLink createAuthLink(CreateAccountAuthorizationRequest request) {
    return create("accounts/authorize", request, AccountAuthorizationLink.class);
  }
}
