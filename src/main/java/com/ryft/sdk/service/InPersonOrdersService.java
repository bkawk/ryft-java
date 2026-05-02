package com.ryft.sdk.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.ryft.sdk.core.QueryParams;
import com.ryft.sdk.core.RyftHttpClient;
import com.ryft.sdk.model.ApiList;
import com.ryft.sdk.model.InPersonOrder;

/**
 * Read-only access to in-person orders.
 */
public final class InPersonOrdersService extends BaseService {
  public InPersonOrdersService(RyftHttpClient client) {
    super(client);
  }

  public JsonNode get(String orderId) {
    return getEntity("in-person/orders/" + orderId);
  }

  public InPersonOrder getOrder(String orderId) {
    return getEntity("in-person/orders/" + orderId, InPersonOrder.class);
  }

  public ApiList list(boolean ascending, Integer limit, String startsAfter) {
    return list("in-person/orders", QueryParams.list(ascending, limit, startsAfter));
  }

  public ApiList<InPersonOrder> listOrders(boolean ascending, Integer limit, String startsAfter) {
    return list("in-person/orders", QueryParams.list(ascending, limit, startsAfter), InPersonOrder.class);
  }
}
