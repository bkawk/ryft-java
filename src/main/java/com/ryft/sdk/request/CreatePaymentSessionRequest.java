package com.ryft.sdk.request;

import com.ryft.sdk.model.IdReference;
import java.util.Map;

public record CreatePaymentSessionRequest(
    Integer amount,
    String currency,
    String customerEmail,
    IdReference customerDetails,
    String paymentType,
    String entryMode,
    String captureFlow,
    String returnUrl,
    Integer platformFee,
    Map<String, Object> splits,
    IdReference previousPayment,
    Map<String, Object> rebillingDetail,
    Map<String, Object> attemptPayment,
    Map<String, Object> metadata
) {
  public static Builder builder(Integer amount, String currency) {
    return new Builder(amount, currency);
  }

  public static final class Builder {
    private final Integer amount;
    private final String currency;
    private String customerEmail;
    private IdReference customerDetails;
    private String paymentType = "Standard";
    private String entryMode = "Online";
    private String captureFlow = "Automatic";
    private String returnUrl;
    private Integer platformFee;
    private Map<String, Object> splits;
    private IdReference previousPayment;
    private Map<String, Object> rebillingDetail;
    private Map<String, Object> attemptPayment;
    private Map<String, Object> metadata;

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

    public Builder paymentType(String paymentType) {
      this.paymentType = paymentType;
      return this;
    }

    public Builder entryMode(String entryMode) {
      this.entryMode = entryMode;
      return this;
    }

    public Builder captureFlow(String captureFlow) {
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

    public Builder splits(Map<String, Object> splits) {
      this.splits = splits;
      return this;
    }

    public Builder previousPaymentId(String previousPaymentId) {
      this.previousPayment = new IdReference(previousPaymentId);
      return this;
    }

    public Builder rebillingDetail(Map<String, Object> rebillingDetail) {
      this.rebillingDetail = rebillingDetail;
      return this;
    }

    public Builder attemptPayment(Map<String, Object> attemptPayment) {
      this.attemptPayment = attemptPayment;
      return this;
    }

    public Builder metadata(Map<String, Object> metadata) {
      this.metadata = metadata;
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
