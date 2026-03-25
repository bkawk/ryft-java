package com.ryft.sdk.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.ryft.sdk.core.QueryParams;
import com.ryft.sdk.core.RyftHttpClient;
import com.ryft.sdk.model.ApiList;
import com.ryft.sdk.model.DeletedResource;
import com.ryft.sdk.model.Person;
import com.ryft.sdk.request.CreatePersonRequest;
import com.ryft.sdk.request.PageRequest;
import com.ryft.sdk.request.UpdatePersonRequest;

/**
 * Person operations for connected Ryft accounts.
 */
public final class PersonsService extends BaseService {
  public PersonsService(RyftHttpClient client) {
    super(client);
  }

  public JsonNode create(String accountId, Object request) {
    return create("accounts/" + accountId + "/persons", request);
  }

  public Person create(String accountId, CreatePersonRequest request) {
    return create("accounts/" + accountId + "/persons", request, Person.class);
  }

  public JsonNode get(String accountId, String personId) {
    return getEntity("accounts/" + accountId + "/persons/" + personId);
  }

  public Person getPerson(String accountId, String personId) {
    return getEntity("accounts/" + accountId + "/persons/" + personId, Person.class);
  }

  public ApiList list(String accountId, boolean ascending, Integer limit, String startsAfter) {
    QueryParams query = QueryParams.list(ascending, limit, startsAfter);
    return list("accounts/" + accountId + "/persons", query);
  }

  public ApiList<Person> listPeople(String accountId, boolean ascending, Integer limit, String startsAfter) {
    QueryParams query = QueryParams.list(ascending, limit, startsAfter);
    return list("accounts/" + accountId + "/persons", query, Person.class);
  }

  /**
   * Lists people on an account using typed pagination options.
   */
  public ApiList<Person> listPeople(String accountId, PageRequest pageRequest) {
    QueryParams query = QueryParams.list(
        pageRequest != null ? Boolean.TRUE.equals(pageRequest.ascending()) : false,
        pageRequest != null ? pageRequest.limit() : null,
        pageRequest != null ? pageRequest.startsAfter() : null
    );
    return list("accounts/" + accountId + "/persons", query, Person.class);
  }

  public JsonNode update(String accountId, String personId, Object request) {
    return patch("accounts/" + accountId + "/persons/" + personId, request);
  }

  public Person update(String accountId, String personId, UpdatePersonRequest request) {
    return patch("accounts/" + accountId + "/persons/" + personId, request.payload(), Person.class);
  }

  public DeletedResource delete(String accountId, String personId) {
    return deleteResource("accounts/" + accountId + "/persons/" + personId);
  }
}
