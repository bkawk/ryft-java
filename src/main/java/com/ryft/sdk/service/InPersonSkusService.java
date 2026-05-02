package com.ryft.sdk.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.ryft.sdk.core.QueryParams;
import com.ryft.sdk.core.RyftHttpClient;
import com.ryft.sdk.model.ApiList;
import com.ryft.sdk.model.InPersonSku;

/**
 * Read-only access to the in-person SKU catalog. Supports country and
 * productId filters in addition to standard pagination.
 */
public final class InPersonSkusService extends BaseService {
  public InPersonSkusService(RyftHttpClient client) {
    super(client);
  }

  public JsonNode get(String skuId) {
    return getEntity("in-person/skus/" + skuId);
  }

  public InPersonSku getSku(String skuId) {
    return getEntity("in-person/skus/" + skuId, InPersonSku.class);
  }

  public ApiList list(boolean ascending, Integer limit, String startsAfter, String country, String productId) {
    return list("in-person/skus", buildQuery(ascending, limit, startsAfter, country, productId));
  }

  public ApiList<InPersonSku> listSkus(
      boolean ascending,
      Integer limit,
      String startsAfter,
      String country,
      String productId
  ) {
    return list(
        "in-person/skus",
        buildQuery(ascending, limit, startsAfter, country, productId),
        InPersonSku.class
    );
  }

  private static QueryParams buildQuery(
      boolean ascending,
      Integer limit,
      String startsAfter,
      String country,
      String productId
  ) {
    return new QueryParams()
        .putBoolean("ascending", ascending)
        .put("limit", limit)
        .put("startsAfter", startsAfter)
        .put("country", country)
        .put("productId", productId);
  }
}
