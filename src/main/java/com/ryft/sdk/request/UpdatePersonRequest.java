package com.ryft.sdk.request;

import com.ryft.sdk.core.Json;
import com.ryft.sdk.model.Metadata;
import java.util.List;
import java.util.Map;

/**
 * Typed connected-account person update payload with a builder-style API.
 */
public record UpdatePersonRequest(
    String firstName,
    String lastName,
    String email,
    String dateOfBirth,
    String gender,
    List<String> nationalities,
    Address address,
    String phoneNumber,
    List<String> businessRoles,
    List<Object> documents,
    Metadata metadata
) {
  public static Builder builder() {
    return new Builder();
  }

  public static UpdatePersonRequest of(Map<String, Object> payload) {
    return Json.MAPPER.convertValue(payload, UpdatePersonRequest.class);
  }

  public static final class Builder {
    private String firstName;
    private String lastName;
    private String email;
    private String dateOfBirth;
    private String gender;
    private List<String> nationalities;
    private Address address;
    private String phoneNumber;
    private List<String> businessRoles;
    private List<Object> documents;
    private Metadata metadata;

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
     * Sets the person's email address.
     */
    public Builder email(String email) {
      this.email = email;
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
    public Builder address(Address address) {
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
    public Builder metadata(Metadata metadata) {
      this.metadata = metadata;
      return this;
    }

    /**
     * Sets application-defined metadata for the person.
     */
    public Builder metadata(Map<String, Object> metadata) {
      this.metadata = Metadata.of(metadata);
      return this;
    }

    public UpdatePersonRequest build() {
      return new UpdatePersonRequest(
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
