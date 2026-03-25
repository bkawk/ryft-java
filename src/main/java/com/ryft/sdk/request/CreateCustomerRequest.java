package com.ryft.sdk.request;

import java.util.Map;

public record CreateCustomerRequest(
    String email,
    String firstName,
    String lastName,
    Map<String, Object> metadata
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
    private Map<String, Object> metadata;

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
    public Builder metadata(Map<String, Object> metadata) {
      this.metadata = metadata;
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
