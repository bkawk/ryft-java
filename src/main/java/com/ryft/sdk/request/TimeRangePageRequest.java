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

    public Builder startTimestamp(Integer startTimestamp) {
      this.startTimestamp = startTimestamp;
      return this;
    }

    public Builder endTimestamp(Integer endTimestamp) {
      this.endTimestamp = endTimestamp;
      return this;
    }

    public Builder ascending(Boolean ascending) {
      this.ascending = ascending;
      return this;
    }

    public Builder limit(Integer limit) {
      this.limit = limit;
      return this;
    }

    public Builder startsAfter(String startsAfter) {
      this.startsAfter = startsAfter;
      return this;
    }

    public TimeRangePageRequest build() {
      return new TimeRangePageRequest(startTimestamp, endTimestamp, ascending, limit, startsAfter);
    }
  }
}
