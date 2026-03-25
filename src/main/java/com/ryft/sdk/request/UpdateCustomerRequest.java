package com.ryft.sdk.request;

import java.util.Map;

public record UpdateCustomerRequest(
    String firstName,
    String lastName,
    Map<String, Object> metadata
) {
  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    private String firstName;
    private String lastName;
    private Map<String, Object> metadata;

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

    public UpdateCustomerRequest build() {
      return new UpdateCustomerRequest(firstName, lastName, metadata);
    }
  }
}
