package com.ryft.sdk.request;

import com.ryft.sdk.core.Json;
import com.ryft.sdk.model.Metadata;
import com.ryft.sdk.model.OnboardingFlow;
import java.util.Map;

/**
 * Typed connected-account update payload with a builder-style API.
 */
public record UpdateAccountRequest(
    String email,
    Metadata metadata,
    OnboardingFlow onboardingFlow,
    TermsOfService termsOfService,
    IndividualDetails individual,
    BusinessDetails business
) {
  public static Builder builder() {
    return new Builder();
  }

  public static UpdateAccountRequest of(Map<String, Object> payload) {
    return Json.MAPPER.convertValue(payload, UpdateAccountRequest.class);
  }

  public static final class Builder {
    private String email;
    private Metadata metadata;
    private OnboardingFlow onboardingFlow;
    private TermsOfService termsOfService;
    private IndividualDetails individual;
    private BusinessDetails business;

    /**
     * Sets the account email address.
     */
    public Builder email(String email) {
      this.email = email;
      return this;
    }

    /**
     * Sets application-defined metadata for the account.
     */
    public Builder metadata(Metadata metadata) {
      this.metadata = metadata;
      return this;
    }

    /**
     * Sets application-defined metadata for the account.
     */
    public Builder metadata(Map<String, Object> metadata) {
      this.metadata = Metadata.of(metadata);
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

    public UpdateAccountRequest build() {
      return new UpdateAccountRequest(email, metadata, onboardingFlow, termsOfService, individual, business);
    }
  }
}
