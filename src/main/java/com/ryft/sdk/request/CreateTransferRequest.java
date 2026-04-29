package com.ryft.sdk.request;

import com.ryft.sdk.model.Metadata;
import java.util.Map;

public record CreateTransferRequest(
    String destinationAccountId,
    Integer amount,
    String currency,
    Metadata metadata
) {
  /**
   * Starts a typed transfer-create builder.
   */
  public static Builder builder(String destinationAccountId, Integer amount, String currency) {
    return new Builder(destinationAccountId, amount, currency);
  }

  public static final class Builder {
    private final String destinationAccountId;
    private final Integer amount;
    private final String currency;
    private Metadata metadata;

    private Builder(String destinationAccountId, Integer amount, String currency) {
      this.destinationAccountId = destinationAccountId;
      this.amount = amount;
      this.currency = currency;
    }

    /**
     * Sets application-defined metadata for the transfer.
     */
    public Builder metadata(Metadata metadata) {
      this.metadata = metadata;
      return this;
    }

    /**
     * Sets application-defined metadata for the transfer.
     */
    public Builder metadata(Map<String, Object> metadata) {
      this.metadata = Metadata.of(metadata);
      return this;
    }

    /**
     * Builds the immutable request payload.
     */
    public CreateTransferRequest build() {
      return new CreateTransferRequest(destinationAccountId, amount, currency, metadata);
    }
  }
}
