package com.ryft.sdk.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.ryft.sdk.core.QueryParams;
import com.ryft.sdk.core.RyftHttpClient;
import com.ryft.sdk.model.ApiList;
import com.ryft.sdk.model.DeletedResource;
import com.ryft.sdk.model.Subscription;
import com.ryft.sdk.request.CreateSubscriptionRequest;
import com.ryft.sdk.request.UpdateSubscriptionRequest;

public final class SubscriptionsService extends BaseService {
  public SubscriptionsService(RyftHttpClient client) {
    super(client);
  }

  public JsonNode create(Object request) {
    return create("subscriptions", request);
  }

  public Subscription create(CreateSubscriptionRequest request) {
    return create("subscriptions", request, Subscription.class);
  }

  public JsonNode get(String subscriptionId) {
    return getEntity("subscriptions/" + subscriptionId);
  }

  public Subscription getSubscription(String subscriptionId) {
    return getEntity("subscriptions/" + subscriptionId, Subscription.class);
  }

  public JsonNode update(String subscriptionId, Object request) {
    return patch("subscriptions/" + subscriptionId, request);
  }

  public Subscription update(String subscriptionId, UpdateSubscriptionRequest request) {
    return patch("subscriptions/" + subscriptionId, request, Subscription.class);
  }

  public ApiList list(Integer startTimestamp, Integer endTimestamp, boolean ascending, Integer limit, String startsAfter) {
    QueryParams query = QueryParams.list(ascending, limit, startsAfter)
        .put("startTimestamp", startTimestamp)
        .put("endTimestamp", endTimestamp);
    return list("subscriptions", query);
  }

  public ApiList<Subscription> listSubscriptions(Integer startTimestamp, Integer endTimestamp, boolean ascending, Integer limit, String startsAfter) {
    QueryParams query = QueryParams.list(ascending, limit, startsAfter)
        .put("startTimestamp", startTimestamp)
        .put("endTimestamp", endTimestamp);
    return list("subscriptions", query, Subscription.class);
  }

  public ApiList getPaymentSessions(String subscriptionId, Integer startTimestamp, Integer endTimestamp, boolean ascending, Integer limit, String startsAfter) {
    QueryParams query = QueryParams.list(ascending, limit, startsAfter)
        .put("startTimestamp", startTimestamp)
        .put("endTimestamp", endTimestamp);
    return list("subscriptions/" + subscriptionId + "/payment-sessions", query);
  }

  public JsonNode pause(String subscriptionId, Object request) {
    return patch("subscriptions/" + subscriptionId + "/pause", request);
  }

  public JsonNode resume(String subscriptionId) {
    return patch("subscriptions/" + subscriptionId + "/resume", null);
  }

  public DeletedResource cancel(String subscriptionId) {
    return deleteResource("subscriptions/" + subscriptionId + "/cancel");
  }
}
