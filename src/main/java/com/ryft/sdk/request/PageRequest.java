package com.ryft.sdk.request;

/**
 * Common pagination options for Ryft list endpoints.
 */
public record PageRequest(Boolean ascending, Integer limit, String startsAfter) {
  /**
   * Creates a builder for a page request.
   */
  public static Builder builder() {
    return new Builder();
  }

  /**
   * Returns the first page with the provided limit.
   */
  public static PageRequest firstPage(Integer limit) {
    return builder().limit(limit).build();
  }

  /**
   * Returns a copy of this request using the provided pagination token.
   */
  public PageRequest nextPage(String paginationToken) {
    return new PageRequest(ascending, limit, paginationToken);
  }

  public static final class Builder {
    private Boolean ascending;
    private Integer limit;
    private String startsAfter;

    /**
     * Sets the sort direction for the list endpoint.
     */
    public Builder ascending(Boolean ascending) {
      this.ascending = ascending;
      return this;
    }

    /**
     * Sets the maximum number of items to return.
     */
    public Builder limit(Integer limit) {
      this.limit = limit;
      return this;
    }

    /**
     * Sets the pagination token returned by a previous page.
     */
    public Builder startsAfter(String startsAfter) {
      this.startsAfter = startsAfter;
      return this;
    }

    /**
     * Builds the immutable page request.
     */
    public PageRequest build() {
      return new PageRequest(ascending, limit, startsAfter);
    }
  }
}
