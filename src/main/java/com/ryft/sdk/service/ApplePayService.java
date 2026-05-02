package com.ryft.sdk.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.ryft.sdk.core.QueryParams;
import com.ryft.sdk.core.RyftHttpClient;
import com.ryft.sdk.model.ApiList;
import com.ryft.sdk.model.ApplePayWebDomain;
import com.ryft.sdk.model.ApplePayWebSession;
import com.ryft.sdk.model.DeletedResource;
import com.ryft.sdk.request.CreateApplePaySessionRequest;
import com.ryft.sdk.request.RegisterApplePayDomainRequest;

/**
 * Apple Pay merchant domain and session operations.
 */
public final class ApplePayService extends BaseService {
  public ApplePayService(RyftHttpClient client) {
    super(client);
  }

  public JsonNode registerDomain(Object request) {
    return create("apple-pay/web-domains", request);
  }

  public ApplePayWebDomain registerDomain(RegisterApplePayDomainRequest request) {
    return create("apple-pay/web-domains", request, ApplePayWebDomain.class);
  }

  public JsonNode getDomain(String domainId) {
    return getEntity("apple-pay/web-domains/" + domainId);
  }

  public ApplePayWebDomain getDomainTyped(String domainId) {
    return getEntity("apple-pay/web-domains/" + domainId, ApplePayWebDomain.class);
  }

  public ApiList listDomains(boolean ascending, Integer limit, String startsAfter) {
    return list("apple-pay/web-domains", QueryParams.list(ascending, limit, startsAfter));
  }

  public ApiList<ApplePayWebDomain> listDomainsTyped(boolean ascending, Integer limit, String startsAfter) {
    return list("apple-pay/web-domains", QueryParams.list(ascending, limit, startsAfter), ApplePayWebDomain.class);
  }

  public DeletedResource deleteDomain(String domainId) {
    return deleteResource("apple-pay/web-domains/" + domainId);
  }

  public JsonNode createSession(Object request) {
    return create("apple-pay/sessions", request);
  }

  public ApplePayWebSession createSession(CreateApplePaySessionRequest request) {
    return create("apple-pay/sessions", request, ApplePayWebSession.class);
  }
}
