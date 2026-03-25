package com.ryft.sdk.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.ryft.sdk.core.Json;
import com.ryft.sdk.core.QueryParams;
import com.ryft.sdk.core.RyftHttpClient;
import com.ryft.sdk.model.ApiList;
import com.ryft.sdk.model.RyftFile;
import com.ryft.sdk.request.PageRequest;
import java.nio.file.Path;

/**
 * File upload and read operations.
 */
public final class FilesService extends BaseService {
  public FilesService(RyftHttpClient client) {
    super(client);
  }

  public ApiList list(String category, boolean ascending, Integer limit, String startsAfter) {
    QueryParams query = QueryParams.list(ascending, limit, startsAfter)
        .put("category", category);
    return list("files", query);
  }

  /**
   * Lists files using typed pagination options.
   */
  public ApiList<RyftFile> listFiles(String category, PageRequest pageRequest) {
    QueryParams query = QueryParams.list(
            pageRequest != null && Boolean.TRUE.equals(pageRequest.ascending()),
            pageRequest != null ? pageRequest.limit() : null,
            pageRequest != null ? pageRequest.startsAfter() : null
        )
        .put("category", category);
    return list("files", query, RyftFile.class);
  }

  public JsonNode get(String fileId) {
    return getEntity("files/" + fileId);
  }

  /**
   * Retrieves a file as a typed model.
   */
  public RyftFile getFile(String fileId) {
    return getEntity("files/" + fileId, RyftFile.class);
  }

  public JsonNode create(Path filePath, String category) {
    return client.postMultipartFile("files", filePath, category, null);
  }

  public JsonNode createForAccount(Path filePath, String category, String accountId) {
    return client.postMultipartFile("files", filePath, category, accountId);
  }

  /**
   * Uploads a file and returns a typed response model.
   */
  public RyftFile upload(Path filePath, String category) {
    return Json.MAPPER.convertValue(client.postMultipartFile("files", filePath, category, null), RyftFile.class);
  }
}
