package com.ryft.sdk.model;

import java.util.List;
import com.ryft.sdk.request.PageRequest;

/**
 * Typed wrapper for Ryft list responses.
 */
public record ApiList<T>(List<T> items, String paginationToken) {
  public ApiList {
    items = items == null ? List.of() : List.copyOf(items);
  }

  /**
   * Returns whether the response advertises another page of results.
   */
  public boolean hasNextPage() {
    return paginationToken != null && !paginationToken.isBlank();
  }

  /**
   * Builds a page request for the next page using the current pagination token.
   */
  public PageRequest nextPageRequest(Integer limit) {
    return PageRequest.builder()
        .limit(limit)
        .startsAfter(paginationToken)
        .build();
  }
}
