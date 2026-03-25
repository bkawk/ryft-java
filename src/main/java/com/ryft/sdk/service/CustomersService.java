package com.ryft.sdk.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.ryft.sdk.core.QueryParams;
import com.ryft.sdk.core.RyftHttpClient;
import com.ryft.sdk.model.ApiList;
import com.ryft.sdk.model.Customer;
import com.ryft.sdk.model.DeletedResource;
import com.ryft.sdk.request.CreateCustomerRequest;
import com.ryft.sdk.request.UpdateCustomerRequest;

public final class CustomersService extends BaseService {
  public CustomersService(RyftHttpClient client) {
    super(client);
  }

  public JsonNode create(Object request) {
    return create("customers", request);
  }

  public Customer create(CreateCustomerRequest request) {
    return create("customers", request, Customer.class);
  }

  public ApiList list(String email, Integer startTimestamp, Integer endTimestamp, boolean ascending, Integer limit, String startsAfter) {
    QueryParams query = QueryParams.list(ascending, limit, startsAfter)
        .put("email", email)
        .put("startTimestamp", startTimestamp)
        .put("endTimestamp", endTimestamp);
    return list("customers", query);
  }

  public ApiList<Customer> listCustomers(String email, Integer startTimestamp, Integer endTimestamp, boolean ascending, Integer limit, String startsAfter) {
    QueryParams query = QueryParams.list(ascending, limit, startsAfter)
        .put("email", email)
        .put("startTimestamp", startTimestamp)
        .put("endTimestamp", endTimestamp);
    return list("customers", query, Customer.class);
  }

  public JsonNode get(String customerId) {
    return getEntity("customers/" + customerId);
  }

  public Customer getCustomer(String customerId) {
    return getEntity("customers/" + customerId, Customer.class);
  }

  public JsonNode update(String customerId, Object request) {
    return patch("customers/" + customerId, request);
  }

  public Customer update(String customerId, UpdateCustomerRequest request) {
    return patch("customers/" + customerId, request, Customer.class);
  }

  public DeletedResource delete(String customerId) {
    return deleteResource("customers/" + customerId);
  }

  public ApiList getPaymentMethods(String customerId) {
    return list("customers/" + customerId + "/payment-methods", null);
  }
}
