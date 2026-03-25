package com.ryft.sdk.request;

/**
 * Business account details.
 */
public record BusinessDetails(
    String name,
    String type,
    String registrationNumber,
    Address registeredAddress,
    String contactEmail
) {
  public static Builder builder(String name, String type, String registrationNumber) {
    return new Builder(name, type, registrationNumber);
  }

  public static final class Builder {
    private final String name;
    private final String type;
    private final String registrationNumber;
    private Address registeredAddress;
    private String contactEmail;

    private Builder(String name, String type, String registrationNumber) {
      this.name = name;
      this.type = type;
      this.registrationNumber = registrationNumber;
    }

    public Builder registeredAddress(Address registeredAddress) {
      this.registeredAddress = registeredAddress;
      return this;
    }

    public Builder contactEmail(String contactEmail) {
      this.contactEmail = contactEmail;
      return this;
    }

    public BusinessDetails build() {
      return new BusinessDetails(name, type, registrationNumber, registeredAddress, contactEmail);
    }
  }
}
