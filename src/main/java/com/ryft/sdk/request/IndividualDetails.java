package com.ryft.sdk.request;

import java.util.List;

/**
 * Individual account details.
 */
public record IndividualDetails(
    String firstName,
    String lastName,
    String email,
    String dateOfBirth,
    String gender,
    List<String> nationalities,
    Address address
) {
  /**
   * Starts a typed individual-details builder.
   */
  public static Builder builder(String firstName, String lastName, String email) {
    return new Builder(firstName, lastName, email);
  }

  public static final class Builder {
    private final String firstName;
    private final String lastName;
    private final String email;
    private String dateOfBirth;
    private String gender;
    private List<String> nationalities;
    private Address address;

    private Builder(String firstName, String lastName, String email) {
      this.firstName = firstName;
      this.lastName = lastName;
      this.email = email;
    }

    /**
     * Sets the date of birth in ISO-8601 format.
     */
    public Builder dateOfBirth(String dateOfBirth) {
      this.dateOfBirth = dateOfBirth;
      return this;
    }

    /**
     * Sets the gender value expected by the API.
     */
    public Builder gender(String gender) {
      this.gender = gender;
      return this;
    }

    /**
     * Sets the individual's nationalities.
     */
    public Builder nationalities(List<String> nationalities) {
      this.nationalities = nationalities;
      return this;
    }

    /**
     * Sets the individual's postal address.
     */
    public Builder address(Address address) {
      this.address = address;
      return this;
    }

    /**
     * Builds the immutable individual-details payload.
     */
    public IndividualDetails build() {
      return new IndividualDetails(firstName, lastName, email, dateOfBirth, gender, nationalities, address);
    }
  }
}
