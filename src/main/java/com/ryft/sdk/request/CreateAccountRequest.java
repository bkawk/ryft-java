package com.ryft.sdk.request;

import java.util.Map;

public record CreateAccountRequest(
    String entityType,
    String email,
    Map<String, Object> metadata,
    String onboardingFlow,
    Map<String, Object> termsOfService,
    Map<String, Object> individual,
    Map<String, Object> business
) {
  public static Builder builder(String entityType, String email) {
    return new Builder(entityType, email);
  }

  public static final class Builder {
    private final String entityType;
    private final String email;
    private Map<String, Object> metadata;
    private String onboardingFlow;
    private Map<String, Object> termsOfService;
    private Map<String, Object> individual;
    private Map<String, Object> business;

    private Builder(String entityType, String email) {
      this.entityType = entityType;
      this.email = email;
    }

    public Builder metadata(Map<String, Object> metadata) {
      this.metadata = metadata;
      return this;
    }

    public Builder onboardingFlow(String onboardingFlow) {
      this.onboardingFlow = onboardingFlow;
      return this;
    }

    public Builder termsOfService(Map<String, Object> termsOfService) {
      this.termsOfService = termsOfService;
      return this;
    }

    public Builder individual(Map<String, Object> individual) {
      this.individual = individual;
      return this;
    }

    public Builder business(Map<String, Object> business) {
      this.business = business;
      return this;
    }

    public CreateAccountRequest build() {
      return new CreateAccountRequest(entityType, email, metadata, onboardingFlow, termsOfService, individual, business);
    }
  }
}
