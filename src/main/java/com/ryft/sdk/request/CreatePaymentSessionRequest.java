package com.ryft.sdk.request;

import com.ryft.sdk.model.CaptureFlow;
import com.ryft.sdk.model.IdReference;
import com.ryft.sdk.model.EntryMode;
import com.ryft.sdk.model.Metadata;
import com.ryft.sdk.model.PaymentType;
import java.util.Map;

/**
 * Typed payment-session create payload with a builder-style API.
 */
public record CreatePaymentSessionRequest(
    Integer amount,
    String currency,
    String customerEmail,
    IdReference customerDetails,
    PaymentType paymentType,
    EntryMode entryMode,
    CaptureFlow captureFlow,
    String returnUrl,
    Integer platformFee,
    PaymentSessionSplitRequest splits,
    IdReference previousPayment,
    RebillingDetailRequest rebillingDetail,
    AttemptPaymentRequest attemptPayment,
    Metadata metadata
) {
  /**
   * Starts a typed payment-session builder.
   */
  public static Builder builder(Integer amount, String currency) {
    return new Builder(amount, currency);
  }

  public static final class Builder {
    private final Integer amount;
    private final String currency;
    private String customerEmail;
    private IdReference customerDetails;
    private PaymentType paymentType = PaymentType.Standard;
    private EntryMode entryMode = EntryMode.Online;
    private CaptureFlow captureFlow = CaptureFlow.Automatic;
    private String returnUrl;
    private Integer platformFee;
    private PaymentSessionSplitRequest splits;
    private IdReference previousPayment;
    private RebillingDetailRequest rebillingDetail;
    private AttemptPaymentRequest attemptPayment;
    private Metadata metadata;

    private Builder(Integer amount, String currency) {
      this.amount = amount;
      this.currency = currency;
    }

    public Builder customerEmail(String customerEmail) {
      this.customerEmail = customerEmail;
      return this;
    }

    public Builder customerId(String customerId) {
      this.customerDetails = new IdReference(customerId);
      return this;
    }

    /**
     * Sets the payment session type.
     */
    public Builder paymentType(PaymentType paymentType) {
      this.paymentType = paymentType;
      return this;
    }

    /**
     * Sets how payment details will be collected.
     */
    public Builder entryMode(EntryMode entryMode) {
      this.entryMode = entryMode;
      return this;
    }

    /**
     * Sets whether the payment should capture automatically or manually.
     */
    public Builder captureFlow(CaptureFlow captureFlow) {
      this.captureFlow = captureFlow;
      return this;
    }

    public Builder returnUrl(String returnUrl) {
      this.returnUrl = returnUrl;
      return this;
    }

    public Builder platformFee(Integer platformFee) {
      this.platformFee = platformFee;
      return this;
    }

    /**
     * Sets split-payment allocations.
     */
    public Builder splits(PaymentSessionSplitRequest splits) {
      this.splits = splits;
      return this;
    }

    public Builder previousPaymentId(String previousPaymentId) {
      this.previousPayment = new IdReference(previousPaymentId);
      return this;
    }

    /**
     * Sets rebilling details for recurring payment attempts.
     */
    public Builder rebillingDetail(RebillingDetailRequest rebillingDetail) {
      this.rebillingDetail = rebillingDetail;
      return this;
    }

    /**
     * Sets a server-side attempt-payment payload.
     */
    public Builder attemptPayment(AttemptPaymentRequest attemptPayment) {
      this.attemptPayment = attemptPayment;
      return this;
    }

    /**
     * Sets application-defined metadata for the payment session.
     */
    public Builder metadata(Metadata metadata) {
      this.metadata = metadata;
      return this;
    }

    /**
     * Sets application-defined metadata for the payment session.
     */
    public Builder metadata(Map<String, Object> metadata) {
      this.metadata = Metadata.of(metadata);
      return this;
    }

    public CreatePaymentSessionRequest build() {
      return new CreatePaymentSessionRequest(
          amount,
          currency,
          customerEmail,
          customerDetails,
          paymentType,
          entryMode,
          captureFlow,
          returnUrl,
          platformFee,
          splits,
          previousPayment,
          rebillingDetail,
          attemptPayment,
          metadata
      );
    }
  }
}
