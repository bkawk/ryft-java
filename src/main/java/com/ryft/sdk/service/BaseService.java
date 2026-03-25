package com.ryft.sdk.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.ryft.sdk.core.Json;
import com.ryft.sdk.core.QueryParams;
import com.ryft.sdk.core.RyftHttpClient;
import com.ryft.sdk.model.ApiList;
import com.ryft.sdk.model.DeletedResource;

public abstract class BaseService {
  protected final RyftHttpClient client;

  protected BaseService(RyftHttpClient client) {
    this.client = client;
  }

  protected JsonNode getEntity(String path) {
    return client.get(path, null, null);
  }

  protected JsonNode getEntity(String path, String accountId) {
    return client.get(path, null, accountId);
  }

  protected ApiList list(String path, QueryParams query) {
    return Json.MAPPER.convertValue(client.get(path, query, null), ApiList.class);
  }

  protected ApiList list(String path, QueryParams query, String accountId) {
    return Json.MAPPER.convertValue(client.get(path, query, accountId), ApiList.class);
  }

  protected JsonNode create(String path, Object request) {
    return client.post(path, request, null);
  }

  protected JsonNode create(String path, Object request, String accountId) {
    return client.post(path, request, accountId);
  }

  protected JsonNode patch(String path, Object request) {
    return client.patch(path, request, null);
  }

  protected JsonNode patch(String path, Object request, String accountId) {
    return client.patch(path, request, accountId);
  }

  protected DeletedResource deleteResource(String path) {
    return Json.MAPPER.convertValue(client.delete(path, null), DeletedResource.class);
  }

  protected DeletedResource deleteResource(String path, String accountId) {
    return Json.MAPPER.convertValue(client.delete(path, accountId), DeletedResource.class);
  }
}
