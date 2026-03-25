package com.ryft.sdk.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.ryft.sdk.core.RyftHttpClient;
import com.ryft.sdk.model.ApiList;
import com.ryft.sdk.model.DeletedResource;
import com.ryft.sdk.model.Webhook;
import com.ryft.sdk.request.CreateWebhookRequest;
import com.ryft.sdk.request.UpdateWebhookRequest;

/**
 * Webhook endpoint management operations.
 */
public final class WebhooksService extends BaseService {
  public WebhooksService(RyftHttpClient client) {
    super(client);
  }

  public JsonNode create(Object request) {
    return create("webhooks", request);
  }

  public Webhook create(CreateWebhookRequest request) {
    return create("webhooks", request, Webhook.class);
  }

  public JsonNode get(String webhookId) {
    return getEntity("webhooks/" + webhookId);
  }

  public Webhook getWebhook(String webhookId) {
    return getEntity("webhooks/" + webhookId, Webhook.class);
  }

  public ApiList list() {
    return list("webhooks", null);
  }

  public ApiList<Webhook> listWebhooks() {
    return list("webhooks", null, Webhook.class);
  }

  public JsonNode update(String webhookId, Object request) {
    return patch("webhooks/" + webhookId, request);
  }

  public Webhook update(String webhookId, UpdateWebhookRequest request) {
    return patch("webhooks/" + webhookId, request, Webhook.class);
  }

  public DeletedResource delete(String webhookId) {
    return deleteResource("webhooks/" + webhookId);
  }
}
