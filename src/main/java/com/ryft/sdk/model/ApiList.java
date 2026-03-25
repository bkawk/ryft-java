package com.ryft.sdk.model;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.List;

public record ApiList(List<JsonNode> items) {
}
