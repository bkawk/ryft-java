package com.ryft.sdk.model;

import java.util.List;

/**
 * Split-payment details returned on a payment session.
 */
public record PaymentSessionSplitDetail(List<PaymentSessionSplitItem> items) {
}
