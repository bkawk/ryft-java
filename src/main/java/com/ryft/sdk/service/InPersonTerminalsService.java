package com.ryft.sdk.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.ryft.sdk.core.QueryParams;
import com.ryft.sdk.core.RyftHttpClient;
import com.ryft.sdk.model.ApiList;
import com.ryft.sdk.model.DeletedResource;
import com.ryft.sdk.model.Terminal;
import com.ryft.sdk.request.CreateTerminalRequest;
import com.ryft.sdk.request.TerminalConfirmReceiptRequest;
import com.ryft.sdk.request.TerminalPaymentRequest;
import com.ryft.sdk.request.TerminalRefundRequest;
import com.ryft.sdk.request.UpdateTerminalRequest;

/**
 * In-person card terminals.
 */
public final class InPersonTerminalsService extends BaseService {
  public InPersonTerminalsService(RyftHttpClient client) {
    super(client);
  }

  public JsonNode create(Object request) {
    return super.create("in-person/terminals", request);
  }

  public Terminal create(CreateTerminalRequest request) {
    return create("in-person/terminals", request, Terminal.class);
  }

  public JsonNode get(String terminalId) {
    return getEntity("in-person/terminals/" + terminalId);
  }

  public Terminal getTerminal(String terminalId) {
    return getEntity("in-person/terminals/" + terminalId, Terminal.class);
  }

  public ApiList list(boolean ascending, Integer limit, String startsAfter) {
    return list("in-person/terminals", QueryParams.list(ascending, limit, startsAfter));
  }

  public ApiList<Terminal> listTerminals(boolean ascending, Integer limit, String startsAfter) {
    return list("in-person/terminals", QueryParams.list(ascending, limit, startsAfter), Terminal.class);
  }

  public JsonNode update(String terminalId, Object request) {
    return patch("in-person/terminals/" + terminalId, request);
  }

  public Terminal update(String terminalId, UpdateTerminalRequest request) {
    return patch("in-person/terminals/" + terminalId, request, Terminal.class);
  }

  public DeletedResource delete(String terminalId) {
    return deleteResource("in-person/terminals/" + terminalId);
  }

  public JsonNode initiatePayment(String terminalId, Object request) {
    return create("in-person/terminals/" + terminalId + "/payment", request);
  }

  public Terminal initiatePayment(String terminalId, TerminalPaymentRequest request) {
    return create("in-person/terminals/" + terminalId + "/payment", request, Terminal.class);
  }

  public JsonNode initiateRefund(String terminalId, Object request) {
    return create("in-person/terminals/" + terminalId + "/refund", request);
  }

  public Terminal initiateRefund(String terminalId, TerminalRefundRequest request) {
    return create("in-person/terminals/" + terminalId + "/refund", request, Terminal.class);
  }

  public JsonNode cancelAction(String terminalId) {
    return create("in-person/terminals/" + terminalId + "/cancel-action", null);
  }

  public Terminal cancelActionTyped(String terminalId) {
    return create("in-person/terminals/" + terminalId + "/cancel-action", null, Terminal.class);
  }

  public JsonNode confirmReceipt(String terminalId, Object request) {
    return create("in-person/terminals/" + terminalId + "/confirm-receipt", request);
  }

  public Terminal confirmReceipt(String terminalId, TerminalConfirmReceiptRequest request) {
    return create("in-person/terminals/" + terminalId + "/confirm-receipt", request, Terminal.class);
  }
}
