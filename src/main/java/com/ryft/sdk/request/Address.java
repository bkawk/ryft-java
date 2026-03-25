package com.ryft.sdk.request;

/**
 * Reusable postal address request payload.
 */
public record Address(
    String lineOne,
    String lineTwo,
    String city,
    String region,
    String country,
    String postalCode
) {
  /**
   * Starts a typed address builder.
   */
  public static Builder builder(String lineOne, String city, String country, String postalCode) {
    return new Builder(lineOne, city, country, postalCode);
  }

  public static final class Builder {
    private final String lineOne;
    private final String city;
    private final String country;
    private final String postalCode;
    private String lineTwo;
    private String region;

    private Builder(String lineOne, String city, String country, String postalCode) {
      this.lineOne = lineOne;
      this.city = city;
      this.country = country;
      this.postalCode = postalCode;
    }

    /**
     * Sets the optional second address line.
     */
    public Builder lineTwo(String lineTwo) {
      this.lineTwo = lineTwo;
      return this;
    }

    /**
     * Sets the region, county, or state.
     */
    public Builder region(String region) {
      this.region = region;
      return this;
    }

    /**
     * Builds the immutable address payload.
     */
    public Address build() {
      return new Address(lineOne, lineTwo, city, region, country, postalCode);
    }
  }
}
