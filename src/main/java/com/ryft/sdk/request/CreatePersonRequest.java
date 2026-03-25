package com.ryft.sdk.request;

import java.util.List;
import java.util.Map;

public record CreatePersonRequest(
    String firstName,
    String lastName,
    String email,
    String dateOfBirth,
    String gender,
    List<String> nationalities,
    Map<String, Object> address,
    String phoneNumber,
    List<String> businessRoles,
    List<Object> documents,
    Map<String, Object> metadata
) {
  public static Builder builder(String email) {
    return new Builder(email);
  }

  public static final class Builder {
    private final String email;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String gender;
    private List<String> nationalities;
    private Map<String, Object> address;
    private String phoneNumber;
    private List<String> businessRoles;
    private List<Object> documents;
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

    public Builder dateOfBirth(String dateOfBirth) {
      this.dateOfBirth = dateOfBirth;
      return this;
    }

    public Builder gender(String gender) {
      this.gender = gender;
      return this;
    }

    public Builder nationalities(List<String> nationalities) {
      this.nationalities = nationalities;
      return this;
    }

    public Builder address(Map<String, Object> address) {
      this.address = address;
      return this;
    }

    public Builder phoneNumber(String phoneNumber) {
      this.phoneNumber = phoneNumber;
      return this;
    }

    public Builder businessRoles(List<String> businessRoles) {
      this.businessRoles = businessRoles;
      return this;
    }

    public Builder documents(List<Object> documents) {
      this.documents = documents;
      return this;
    }

    public Builder metadata(Map<String, Object> metadata) {
      this.metadata = metadata;
      return this;
    }

    public CreatePersonRequest build() {
      return new CreatePersonRequest(
          firstName,
          lastName,
          email,
          dateOfBirth,
          gender,
          nationalities,
          address,
          phoneNumber,
          businessRoles,
          documents,
          metadata
      );
    }
  }
}
