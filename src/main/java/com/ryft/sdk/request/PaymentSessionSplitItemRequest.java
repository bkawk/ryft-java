package com.ryft.sdk.request;

import com.ryft.sdk.model.Metadata;
import java.util.Map;

/**
 * One destination allocation within a split payment request.
 */
public record PaymentSessionSplitItemRequest(
    String accountId,
    Integer amount,
    String description,
    FeeRequest fee,
    Metadata metadata
) {
  /**
   * Starts a typed split-allocation builder.
   */
  public static Builder builder(String accountId, Integer amount) {
    return new Builder(accountId, amount);
  }

  public static final class Builder {
    private final String accountId;
    private final Integer amount;
    private String description;
    private FeeRequest fee;
    private Metadata metadata;

    private Builder(String accountId, Integer amount) {
      this.accountId = accountId;
      this.amount = amount;
    }

    /**
     * Sets the destination split description.
     */
    public Builder description(String description) {
      this.description = description;
      return this;
    }

    /**
     * Sets fee information for the split allocation.
     */
    public Builder fee(FeeRequest fee) {
      this.fee = fee;
      return this;
    }

    /**
     * Sets application-defined metadata for the split allocation.
     */
    public Builder metadata(Metadata metadata) {
      this.metadata = metadata;
      return this;
    }

    /**
     * Sets application-defined metadata for the split allocation.
     */
    public Builder metadata(Map<String, Object> metadata) {
      this.metadata = Metadata.of(metadata);
      return this;
    }

    /**
     * Builds the immutable split-allocation payload.
     */
    public PaymentSessionSplitItemRequest build() {
      return new PaymentSessionSplitItemRequest(accountId, amount, description, fee, metadata);
    }
  }
}
