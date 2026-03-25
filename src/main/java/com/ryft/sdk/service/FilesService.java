package com.ryft.sdk.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.ryft.sdk.core.QueryParams;
import com.ryft.sdk.core.RyftHttpClient;
import com.ryft.sdk.model.ApiList;
import java.nio.file.Path;

public final class FilesService extends BaseService {
  public FilesService(RyftHttpClient client) {
    super(client);
  }

  public ApiList list(String category, boolean ascending, Integer limit, String startsAfter) {
    QueryParams query = QueryParams.list(ascending, limit, startsAfter)
        .put("category", category);
    return list("files", query);
  }

  public JsonNode get(String fileId) {
    return getEntity("files/" + fileId);
  }

  public JsonNode create(Path filePath, String category) {
    return client.postMultipartFile("files", filePath, category, null);
  }

  public JsonNode createForAccount(Path filePath, String category, String accountId) {
    return client.postMultipartFile("files", filePath, category, accountId);
  }
}
