package com.ryft.sdk.request;

import java.util.Map;

/**
 * Request payload to initiate a payment on a terminal.
 */
public record TerminalPaymentRequest(
    RequestedAmounts amounts,
    String currency,
    TerminalPaymentSessionRequest paymentSession,
    Map<String, Object> settings
) {
}
