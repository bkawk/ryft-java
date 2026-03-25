package com.ryft.sdk.request;

import com.ryft.sdk.model.AccountEntityType;
import com.ryft.sdk.model.OnboardingFlow;
import java.util.Map;

/**
 * Typed connected-account create payload with a builder-style API.
 */
public record CreateAccountRequest(
    AccountEntityType entityType,
    String email,
    Map<String, Object> metadata,
    OnboardingFlow onboardingFlow,
    TermsOfService termsOfService,
    IndividualDetails individual,
    BusinessDetails business
) {
  /**
   * Starts a typed connected-account builder.
   */
  public static Builder builder(AccountEntityType entityType, String email) {
    return new Builder(entityType, email);
  }

  public static final class Builder {
    private final AccountEntityType entityType;
    private final String email;
    private Map<String, Object> metadata;
    private OnboardingFlow onboardingFlow;
    private TermsOfService termsOfService;
    private IndividualDetails individual;
    private BusinessDetails business;

    private Builder(AccountEntityType entityType, String email) {
      this.entityType = entityType;
      this.email = email;
    }

    public Builder metadata(Map<String, Object> metadata) {
      this.metadata = metadata;
      return this;
    }

    /**
     * Sets the onboarding flow.
     */
    public Builder onboardingFlow(OnboardingFlow onboardingFlow) {
      this.onboardingFlow = onboardingFlow;
      return this;
    }

    /**
     * Sets the terms-of-service payload.
     */
    public Builder termsOfService(TermsOfService termsOfService) {
      this.termsOfService = termsOfService;
      return this;
    }

    /**
     * Sets individual-specific account details.
     */
    public Builder individual(IndividualDetails individual) {
      this.individual = individual;
      return this;
    }

    /**
     * Sets business-specific account details.
     */
    public Builder business(BusinessDetails business) {
      this.business = business;
      return this;
    }

    public CreateAccountRequest build() {
      return new CreateAccountRequest(entityType, email, metadata, onboardingFlow, termsOfService, individual, business);
    }
  }
}
