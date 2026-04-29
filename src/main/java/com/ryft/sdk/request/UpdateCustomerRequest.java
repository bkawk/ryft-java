package com.ryft.sdk.request;

import com.ryft.sdk.model.Metadata;
import java.util.Map;

public record UpdateCustomerRequest(
    String firstName,
    String lastName,
    Metadata metadata
) {
  /**
   * Starts a typed customer-update builder.
   */
  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    private String firstName;
    private String lastName;
    private Metadata metadata;

    /**
     * Sets the customer's first name.
     */
    public Builder firstName(String firstName) {
      this.firstName = firstName;
      return this;
    }

    /**
     * Sets the customer's last name.
     */
    public Builder lastName(String lastName) {
      this.lastName = lastName;
      return this;
    }

    /**
     * Sets application-defined metadata to attach to the customer.
     */
    public Builder metadata(Metadata metadata) {
      this.metadata = metadata;
      return this;
    }

    /**
     * Sets application-defined metadata to attach to the customer.
     */
    public Builder metadata(Map<String, Object> metadata) {
      this.metadata = Metadata.of(metadata);
      return this;
    }

    /**
     * Builds the immutable request payload.
     */
    public UpdateCustomerRequest build() {
      return new UpdateCustomerRequest(firstName, lastName, metadata);
    }
  }
}
