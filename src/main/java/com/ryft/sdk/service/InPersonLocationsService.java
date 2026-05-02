package com.ryft.sdk.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.ryft.sdk.core.QueryParams;
import com.ryft.sdk.core.RyftHttpClient;
import com.ryft.sdk.model.ApiList;
import com.ryft.sdk.model.DeletedResource;
import com.ryft.sdk.model.InPersonLocation;
import com.ryft.sdk.request.CreateInPersonLocationRequest;
import com.ryft.sdk.request.UpdateInPersonLocationRequest;

/**
 * In-person retail locations.
 */
public final class InPersonLocationsService extends BaseService {
  public InPersonLocationsService(RyftHttpClient client) {
    super(client);
  }

  public JsonNode create(Object request) {
    return super.create("in-person/locations", request);
  }

  public InPersonLocation create(CreateInPersonLocationRequest request) {
    return create("in-person/locations", request, InPersonLocation.class);
  }

  public JsonNode get(String locationId) {
    return getEntity("in-person/locations/" + locationId);
  }

  public InPersonLocation getLocation(String locationId) {
    return getEntity("in-person/locations/" + locationId, InPersonLocation.class);
  }

  public ApiList list(boolean ascending, Integer limit, String startsAfter) {
    return list("in-person/locations", QueryParams.list(ascending, limit, startsAfter));
  }

  public ApiList<InPersonLocation> listLocations(boolean ascending, Integer limit, String startsAfter) {
    return list("in-person/locations", QueryParams.list(ascending, limit, startsAfter), InPersonLocation.class);
  }

  public JsonNode update(String locationId, Object request) {
    return patch("in-person/locations/" + locationId, request);
  }

  public InPersonLocation update(String locationId, UpdateInPersonLocationRequest request) {
    return patch("in-person/locations/" + locationId, request, InPersonLocation.class);
  }

  public DeletedResource delete(String locationId) {
    return deleteResource("in-person/locations/" + locationId);
  }
}
