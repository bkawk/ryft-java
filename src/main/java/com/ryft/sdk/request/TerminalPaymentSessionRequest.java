package com.ryft.sdk.request;

import com.ryft.sdk.model.Metadata;
import java.util.Map;

/**
 * Optional payment-session settings for a terminal payment request.
 */
public record TerminalPaymentSessionRequest(
    Integer platformFee,
    Metadata metadata,
    Map<String, Object> paymentSettings
) {
}
