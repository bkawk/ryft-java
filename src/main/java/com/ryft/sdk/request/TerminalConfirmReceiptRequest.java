package com.ryft.sdk.request;

/**
 * Request payload to confirm receipt delivery on a terminal payment.
 */
public record TerminalConfirmReceiptRequest(
    ReceiptCopyStatus customerCopy,
    ReceiptCopyStatus merchantCopy
) {
}
