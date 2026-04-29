package com.ryft.sdk.request;

import com.ryft.sdk.model.Metadata;
import java.util.Map;

public record CreateCustomerRequest(
    String email,
    String firstName,
    String lastName,
    Metadata metadata
) {
  /**
   * Starts a typed customer-create builder.
   */
  public static Builder builder(String email) {
    return new Builder(email);
  }

  public static final class Builder {
    private final String email;
    private String firstName;
    private String lastName;
    private Metadata metadata;

    private Builder(String email) {
      this.email = email;
    }

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
    public CreateCustomerRequest build() {
      return new CreateCustomerRequest(email, firstName, lastName, metadata);
    }
  }
}
