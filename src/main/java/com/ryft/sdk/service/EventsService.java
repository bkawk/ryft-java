package com.ryft.sdk.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.ryft.sdk.core.QueryParams;
import com.ryft.sdk.core.RyftHttpClient;
import com.ryft.sdk.model.ApiList;
import com.ryft.sdk.model.Event;
import com.ryft.sdk.request.PageRequest;

/**
 * Event read operations.
 */
public final class EventsService extends BaseService {
  public EventsService(RyftHttpClient client) {
    super(client);
  }

  public ApiList list(boolean ascending, Integer limit, String accountId) {
    QueryParams query = new QueryParams()
        .putBoolean("ascending", ascending)
        .put("limit", limit);
    return list("events", query, accountId);
  }

  /**
   * Lists events using typed pagination options.
   */
  public ApiList<Event> listEvents(PageRequest pageRequest, String accountId) {
    QueryParams query = new QueryParams()
        .putBoolean("ascending", pageRequest != null && Boolean.TRUE.equals(pageRequest.ascending()))
        .put("limit", pageRequest != null ? pageRequest.limit() : null)
        .put("startsAfter", pageRequest != null ? pageRequest.startsAfter() : null);
    return list("events", query, accountId, Event.class);
  }

  public JsonNode get(String eventId, String accountId) {
    return getEntity("events/" + eventId, accountId);
  }

  /**
   * Retrieves an event as a typed model.
   */
  public Event getEvent(String eventId, String accountId) {
    return getEntity("events/" + eventId, accountId, Event.class);
  }
}
