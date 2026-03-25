package com.ryft.sdk.request;

import java.util.Map;

public record CreateCustomerRequest(
    String email,
    String firstName,
    String lastName,
    Map<String, Object> metadata
) {
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

    public Builder firstName(String firstName) {
      this.firstName = firstName;
      return this;
    }

    public Builder lastName(String lastName) {
      this.lastName = lastName;
      return this;
    }

    public Builder metadata(Map<String, Object> metadata) {
      this.metadata = metadata;
      return this;
    }

    public CreateCustomerRequest build() {
      return new CreateCustomerRequest(email, firstName, lastName, metadata);
    }
  }
}
