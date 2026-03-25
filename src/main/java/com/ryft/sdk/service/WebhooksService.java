package com.ryft.sdk.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.ryft.sdk.core.RyftHttpClient;
import com.ryft.sdk.model.ApiList;
import com.ryft.sdk.model.DeletedResource;

public final class WebhooksService extends BaseService {
  public WebhooksService(RyftHttpClient client) {
    super(client);
  }

  public JsonNode create(Object request) {
    return create("webhooks", request);
  }

  public JsonNode get(String webhookId) {
    return getEntity("webhooks/" + webhookId);
  }

  public ApiList list() {
    return list("webhooks", null);
  }

  public JsonNode update(String webhookId, Object request) {
    return patch("webhooks/" + webhookId, request);
  }

  public DeletedResource delete(String webhookId) {
    return deleteResource("webhooks/" + webhookId);
  }
}
