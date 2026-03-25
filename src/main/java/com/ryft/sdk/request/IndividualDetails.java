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

    public Builder address(Address address) {
      this.address = address;
      return this;
    }

    public IndividualDetails build() {
      return new IndividualDetails(firstName, lastName, email, dateOfBirth, gender, nationalities, address);
    }
  }
}
