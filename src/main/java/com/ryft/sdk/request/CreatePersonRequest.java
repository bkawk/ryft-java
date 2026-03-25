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
  /**
   * Starts a typed person-create builder.
   */
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

    /**
     * Sets the person's first name.
     */
    public Builder firstName(String firstName) {
      this.firstName = firstName;
      return this;
    }

    /**
     * Sets the person's last name.
     */
    public Builder lastName(String lastName) {
      this.lastName = lastName;
      return this;
    }

    /**
     * Sets the date of birth in ISO-8601 format.
     */
    public Builder dateOfBirth(String dateOfBirth) {
      this.dateOfBirth = dateOfBirth;
      return this;
    }

    /**
     * Sets the person's gender value expected by the API.
     */
    public Builder gender(String gender) {
      this.gender = gender;
      return this;
    }

    /**
     * Sets the person's nationalities.
     */
    public Builder nationalities(List<String> nationalities) {
      this.nationalities = nationalities;
      return this;
    }

    /**
     * Sets the person's postal address payload.
     */
    public Builder address(Map<String, Object> address) {
      this.address = address;
      return this;
    }

    /**
     * Sets the person's phone number.
     */
    public Builder phoneNumber(String phoneNumber) {
      this.phoneNumber = phoneNumber;
      return this;
    }

    /**
     * Sets the person's business roles.
     */
    public Builder businessRoles(List<String> businessRoles) {
      this.businessRoles = businessRoles;
      return this;
    }

    /**
     * Sets document metadata for the person.
     */
    public Builder documents(List<Object> documents) {
      this.documents = documents;
      return this;
    }

    /**
     * Sets application-defined metadata for the person.
     */
    public Builder metadata(Map<String, Object> metadata) {
      this.metadata = metadata;
      return this;
    }

    /**
     * Builds the immutable request payload.
     */
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
