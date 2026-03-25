package com.ryft.sdk.model;

/**
 * File upload response model.
 */
public record RyftFile(
    String id,
    String category,
    String fileName
) {
}
