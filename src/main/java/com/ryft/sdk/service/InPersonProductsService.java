package com.ryft.sdk.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.ryft.sdk.core.QueryParams;
import com.ryft.sdk.core.RyftHttpClient;
import com.ryft.sdk.model.ApiList;
import com.ryft.sdk.model.InPersonProduct;

/**
 * Read-only access to the in-person product catalog.
 */
public final class InPersonProductsService extends BaseService {
  public InPersonProductsService(RyftHttpClient client) {
    super(client);
  }

  public JsonNode get(String productId) {
    return getEntity("in-person/products/" + productId);
  }

  public InPersonProduct getProduct(String productId) {
    return getEntity("in-person/products/" + productId, InPersonProduct.class);
  }

  public ApiList list(boolean ascending, Integer limit, String startsAfter) {
    return list("in-person/products", QueryParams.list(ascending, limit, startsAfter));
  }

  public ApiList<InPersonProduct> listProducts(boolean ascending, Integer limit, String startsAfter) {
    return list("in-person/products", QueryParams.list(ascending, limit, startsAfter), InPersonProduct.class);
  }
}
