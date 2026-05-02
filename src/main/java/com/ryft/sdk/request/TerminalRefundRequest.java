package com.ryft.sdk.request;

import java.util.Map;

/**
 * Request payload to initiate a refund on a terminal.
 */
public record TerminalRefundRequest(
    TerminalRefundPaymentSessionReference paymentSession,
    Integer amount,
    Boolean refundPlatformFee,
    Map<String, Object> settings
) {
}
