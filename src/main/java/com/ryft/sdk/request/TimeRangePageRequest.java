package com.ryft.sdk.request;

/**
 * Pagination options for list endpoints that also support a timestamp window.
 */
public record TimeRangePageRequest(
    Integer startTimestamp,
    Integer endTimestamp,
    Boolean ascending,
    Integer limit,
    String startsAfter
) {
  /**
   * Creates a builder for a time-ranged page request.
   */
  public static Builder builder() {
    return new Builder();
  }

  /**
   * Returns a copy of this request using the provided pagination token.
   */
  public TimeRangePageRequest nextPage(String paginationToken) {
    return new TimeRangePageRequest(startTimestamp, endTimestamp, ascending, limit, paginationToken);
  }

  public static final class Builder {
    private Integer startTimestamp;
    private Integer endTimestamp;
    private Boolean ascending;
    private Integer limit;
    private String startsAfter;

    /**
     * Sets the inclusive lower timestamp bound.
     */
    public Builder startTimestamp(Integer startTimestamp) {
      this.startTimestamp = startTimestamp;
      return this;
    }

    /**
     * Sets the inclusive upper timestamp bound.
     */
    public Builder endTimestamp(Integer endTimestamp) {
      this.endTimestamp = endTimestamp;
      return this;
    }

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
     * Builds the immutable time-ranged page request.
     */
    public TimeRangePageRequest build() {
      return new TimeRangePageRequest(startTimestamp, endTimestamp, ascending, limit, startsAfter);
    }
  }
}
