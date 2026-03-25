package com.ryft.sdk.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.ryft.sdk.core.QueryParams;
import com.ryft.sdk.core.RyftHttpClient;
import com.ryft.sdk.model.ApiList;
import com.ryft.sdk.model.DeletedResource;

public final class PersonsService extends BaseService {
  public PersonsService(RyftHttpClient client) {
    super(client);
  }

  public JsonNode create(String accountId, Object request) {
    return create("accounts/" + accountId + "/persons", request);
  }

  public JsonNode get(String accountId, String personId) {
    return getEntity("accounts/" + accountId + "/persons/" + personId);
  }

  public ApiList list(String accountId, boolean ascending, Integer limit, String startsAfter) {
    QueryParams query = QueryParams.list(ascending, limit, startsAfter);
    return list("accounts/" + accountId + "/persons", query);
  }

  public JsonNode update(String accountId, String personId, Object request) {
    return patch("accounts/" + accountId + "/persons/" + personId, request);
  }

  public DeletedResource delete(String accountId, String personId) {
    return deleteResource("accounts/" + accountId + "/persons/" + personId);
  }
}
