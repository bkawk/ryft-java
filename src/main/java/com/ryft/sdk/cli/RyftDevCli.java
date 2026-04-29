package com.ryft.sdk.cli;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.ryft.sdk.RyftClient;
import com.ryft.sdk.core.Json;
import com.ryft.sdk.model.ApiError;
import com.ryft.sdk.model.ApiList;
import com.ryft.sdk.request.RefundPaymentSessionRequest;
import java.io.IOException;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class RyftDevCli {
  private static final String DEFAULT_RETURN_URL = "https://example.com/return";

  private RyftDevCli() {
  }

  public static void main(String[] args) {
    try {
      run(args);
    } catch (ApiError error) {
      printJson(error, true);
      System.exit(1);
    } catch (Exception error) {
      System.err.println(error.getMessage());
      System.exit(1);
    }
  }

  private static void run(String[] args) throws Exception {
    if (args.length == 0) {
      throw new IllegalArgumentException("usage: ryft-dev <command> ...");
    }

    RyftClient client = new RyftClient(requiredEnv("RYFT_SECRET_KEY"));
    String command = args[0];

    switch (command) {
      case "customer-create" -> handleCustomerCreate(client, args);
      case "customer-update" -> handleCustomerUpdate(client, args);
      case "entity-get" -> handleEntityGet(client, args);
      case "payment-session-create" -> handlePaymentSessionCreate(client, args);
      case "payment-session-update" -> handlePaymentSessionUpdate(client, args);
      case "payment-session-refund" -> handlePaymentSessionRefund(client, args);
      case "webhook-create" -> handleWebhookCreate(client, args);
      case "webhook-update" -> handleWebhookUpdate(client, args);
      case "webhook-delete" -> printJson(client.webhooks().delete(requiredArg(args, 1, "webhook id")), false);
      case "webhook-list" -> printJson(client.webhooks().list(), false);
      case "account-create" -> handleAccountCreate(client, args);
      case "account-verify" -> printJson(client.accounts().verify(requiredArg(args, 1, "account id")), false);
      case "account-authorize" -> handleAccountAuthorize(client, args);
      case "person-create" -> handlePersonCreate(client, args);
      case "person-list" -> handlePersonList(client, args);
      case "payout-method-create" -> handlePayoutMethodCreate(client, args);
      case "payout-method-list" -> handlePayoutMethodList(client, args);
      case "payout-create" -> handlePayoutCreate(client, args);
      case "transfer-create" -> handleTransferCreate(client, args);
      case "transfer-list" -> printJson(client.transfers().list(parseIntArg(args, 1, "limit")), false);
      case "balance-list" -> printJson(client.balances().list(requiredArg(args, 1, "currency"), requiredArg(args, 2, "account id")), false);
      case "balance-transaction-list" -> printJson(
          client.balanceTransactions().list(parseIntArg(args, 1, "limit"), "", "", requiredArg(args, 2, "account id")),
          false
      );
      case "event-list" -> printJson(client.events().list(false, 50, optionalArg(args, 1)), false);
      case "platform-fee-list" -> printJson(client.platformFees().list(false, 50), false);
      case "platform-fee-refund-list" -> printJson(client.platformFees().getRefunds(requiredArg(args, 1, "platform fee id")), false);
      case "file-list" -> printJson(client.files().list(optionalArg(args, 1), false, 50, ""), false);
      case "file-create" -> handleFileCreate(client, args);
      case "dispute-list" -> printJson(client.disputes().list(0, 0, false, 50, ""), false);
      case "dispute-accept" -> printJson(client.disputes().accept(requiredArg(args, 1, "dispute id")), false);
      case "dispute-challenge" -> printJson(client.disputes().challenge(requiredArg(args, 1, "dispute id")), false);
      case "dispute-add-evidence" -> handleDisputeAddEvidence(client, args);
      case "dispute-delete-evidence" -> handleDisputeDeleteEvidence(client, args);
      case "customer-payment-method-list" -> printJson(client.customers().getPaymentMethods(requiredArg(args, 1, "customer id")), false);
      case "payment-method-update" -> handlePaymentMethodUpdate(client, args);
      case "payment-method-delete" -> printJson(client.paymentMethods().delete(requiredArg(args, 1, "payment method id")), false);
      case "account-link-create" -> handleAccountLinkCreate(client, args);
      case "subscription-create" -> handleSubscriptionCreate(client, args);
      case "subscription-update" -> handleSubscriptionUpdate(client, args);
      case "subscription-list" -> handleSubscriptionList(client);
      case "subscription-payment-session-list" -> handleSubscriptionPaymentSessionList(client, args);
      case "subscription-pause" -> handleSubscriptionPause(client, args);
      case "subscription-resume" -> printJson(client.subscriptions().resume(requiredArg(args, 1, "subscription id")), false);
      case "subscription-cancel" -> printJson(client.subscriptions().cancel(requiredArg(args, 1, "subscription id")), false);
      default -> throw new IllegalArgumentException("unknown command: " + command);
    }
  }

  private static void handleCustomerCreate(RyftClient client, String[] args) {
    Map<String, Object> request = new LinkedHashMap<>();
    request.put("email", requiredArg(args, 1, "email"));
    putIfNotBlank(request, "firstName", optionalArg(args, 2));
    putIfNotBlank(request, "lastName", optionalArg(args, 3));
    if (args.length > 4 && !args[4].isBlank()) {
      request.put("metadata", parseMap(args[4]));
    }
    printJson(client.customers().create(request), false);
  }

  private static void handleCustomerUpdate(RyftClient client, String[] args) {
    String id = requiredArg(args, 1, "customer id");
    Map<String, Object> request = new LinkedHashMap<>();
    putIfNotBlank(request, "firstName", optionalArg(args, 2));
    putIfNotBlank(request, "lastName", optionalArg(args, 3));
    if (args.length > 4 && !args[4].isBlank()) {
      request.put("metadata", parseMap(args[4]));
    }
    printJson(client.customers().update(id, request), false);
  }

  private static void handleEntityGet(RyftClient client, String[] args) {
    String entityType = requiredArg(args, 1, "entity type");
    String id = requiredArg(args, 2, "entity id");
    String parentId = optionalArg(args, 3);

    Object entity = switch (entityType) {
      case "customer" -> client.customers().get(id);
      case "payment-session" -> parentId == null ? client.paymentSessions().get(id) : client.paymentSessions().getForAccount(id, parentId);
      case "webhook" -> client.webhooks().get(id);
      case "account" -> client.accounts().get(id);
      case "person" -> client.persons().get(requireValue(parentId, "person requires parent account id"), id);
      case "payout-method" -> client.payoutMethods().get(requireValue(parentId, "payout-method requires parent account id"), id);
      case "payout" -> client.payouts().get(requireValue(parentId, "payout requires parent account id"), id);
      case "transfer" -> client.transfers().get(id);
      case "payment-method" -> client.paymentMethods().get(id);
      case "subscription" -> client.subscriptions().get(id);
      case "event" -> client.events().get(id, parentId);
      case "payment-transaction" -> client.paymentSessions().getTransaction(requireValue(parentId, "payment-transaction requires parent payment-session id"), id);
      case "platform-fee" -> client.platformFees().get(id);
      case "file" -> client.files().get(id);
      case "dispute" -> client.disputes().get(id);
      default -> throw new IllegalArgumentException("unsupported entity type: " + entityType);
    };

    printJson(entity, false);
  }

  private static void handlePaymentSessionCreate(RyftClient client, String[] args) {
    AccountScopedRequest request = buildPaymentSessionCreateRequest(parseMap(requiredArg(args, 1, "options json")));
    Object response = request.accountId() == null
        ? client.paymentSessions().create(request.request())
        : client.paymentSessions().createForAccount(request.request(), request.accountId());
    printJson(response, false);
  }

  private static void handlePaymentSessionUpdate(RyftClient client, String[] args) {
    String id = requiredArg(args, 1, "payment session id");
    AccountScopedRequest request = buildAccountScopedRequest(parseMap(requiredArg(args, 2, "options json")));
    Object response = request.accountId() == null
        ? client.paymentSessions().update(id, request.request())
        : client.paymentSessions().updateForAccount(id, request.request(), request.accountId());
    printJson(response, false);
  }

  private static void handlePaymentSessionRefund(RyftClient client, String[] args) {
    String id = requiredArg(args, 1, "payment session id");
    Map<String, Object> raw = args.length > 2 && !args[2].isBlank() ? parseMap(args[2]) : Map.of();
    RefundPaymentSessionRequest request = RefundPaymentSessionRequest.builder()
        .amount(intOrNull(raw.get("amount")))
        .reason(stringOrNull(raw.get("reason")))
        .refundPlatformFee(boolOrNull(raw.get("refundPlatformFee")))
        .build();
    printJson(client.paymentSessions().refund(id, request), false);
  }

  private static void handleWebhookCreate(RyftClient client, String[] args) {
    Map<String, Object> request = new LinkedHashMap<>();
    request.put("url", requiredArg(args, 1, "url"));
    request.put("active", Boolean.parseBoolean(requiredArg(args, 2, "active")));
    request.put("eventTypes", parseList(requiredArg(args, 3, "event types json")));
    printJson(client.webhooks().create(request), false);
  }

  private static void handleWebhookUpdate(RyftClient client, String[] args) {
    String id = requiredArg(args, 1, "webhook id");
    Map<String, Object> request = new LinkedHashMap<>();
    putIfNotBlank(request, "url", optionalArg(args, 2));
    if (args.length > 3 && !args[3].isBlank()) {
      request.put("active", Boolean.parseBoolean(args[3]));
    }
    if (args.length > 4 && !args[4].isBlank()) {
      request.put("eventTypes", parseList(args[4]));
    }
    printJson(client.webhooks().update(id, request), false);
  }

  private static void handleAccountCreate(RyftClient client, String[] args) {
    String entityType = requiredArg(args, 1, "entity type");
    String email = requiredArg(args, 2, "email");
    Map<String, Object> request = new LinkedHashMap<>();
    request.put("entityType", entityType);
    request.put("email", email);
    request.put("onboardingFlow", blankToDefault(optionalArg(args, 5), "NonHosted"));
    request.put("termsOfService", Map.of("acceptance", Map.of("ipAddress", "127.0.0.1")));
    if (args.length > 3 && !args[3].isBlank()) {
      request.put("metadata", parseMap(args[3]));
    }
    request.put(entityType.equalsIgnoreCase("Individual") ? "individual" : "business", defaultAccountBody(entityType, email));
    printJson(client.accounts().create(request), false);
  }

  private static void handleAccountAuthorize(RyftClient client, String[] args) {
    Map<String, Object> request = Map.of(
        "email", requiredArg(args, 1, "email"),
        "redirectUrl", requiredArg(args, 2, "redirect url")
    );
    printJson(client.accounts().createAuthLink(request), false);
  }

  private static void handlePersonCreate(RyftClient client, String[] args) {
    String accountId = requiredArg(args, 1, "account id");
    String email = requiredArg(args, 2, "email");
    Map<String, Object> request = new LinkedHashMap<>();
    request.put("firstName", "Sdk");
    request.put("lastName", "Person");
    request.put("email", email);
    request.put("dateOfBirth", "1990-01-01");
    request.put("gender", "Male");
    request.put("nationalities", List.of("GB"));
    request.put("address", defaultAddress());
    request.put("phoneNumber", "+447000000000");
    request.put("businessRoles", List.of("Director"));
    request.put("documents", List.of());
    if (args.length > 3 && !args[3].isBlank()) {
      request.put("metadata", parseMap(args[3]));
    }
    printJson(client.persons().create(accountId, request), false);
  }

  private static void handlePersonList(RyftClient client, String[] args) {
    printJson(client.persons().list(requiredArg(args, 2, "account id"), true, parseIntArg(args, 1, "limit"), ""), false);
  }

  private static void handlePayoutMethodCreate(RyftClient client, String[] args) {
    String accountId = requiredArg(args, 1, "account id");
    String displayName = requiredArg(args, 2, "display name");
    Map<String, Object> request = Map.of(
        "type", "BankAccount",
        "displayName", displayName,
        "currency", "GBP",
        "country", "GB",
        "bankAccount", Map.of(
            "accountNumberType", "UnitedKingdom",
            "accountNumber", "31926819",
            "bankIdType", "SortCode",
            "bankId", "601613"
        )
    );
    printJson(client.payoutMethods().create(accountId, request), false);
  }

  private static void handlePayoutMethodList(RyftClient client, String[] args) {
    printJson(client.payoutMethods().list(requiredArg(args, 2, "account id"), true, parseIntArg(args, 1, "limit"), ""), false);
  }

  private static void handlePayoutCreate(RyftClient client, String[] args) {
    String accountId = requiredArg(args, 1, "account id");
    Map<String, Object> request = new LinkedHashMap<>();
    request.put("amount", parseIntArg(args, 2, "amount"));
    request.put("currency", requiredArg(args, 3, "currency"));
    request.put("payoutMethodId", requiredArg(args, 4, "payout method id"));
    if (args.length > 5 && !args[5].isBlank()) {
      request.put("metadata", parseMap(args[5]));
    }
    printJson(client.payouts().create(accountId, request), false);
  }

  private static void handleTransferCreate(RyftClient client, String[] args) {
    Map<String, Object> request = new LinkedHashMap<>();
    request.put("destination", Map.of("accountId", requiredArg(args, 1, "destination account id")));
    request.put("amount", parseIntArg(args, 2, "amount"));
    request.put("currency", requiredArg(args, 3, "currency"));
    if (args.length > 4 && !args[4].isBlank()) {
      request.put("metadata", parseMap(args[4]));
    }
    printJson(client.transfers().create(request), false);
  }

  private static void handleFileCreate(RyftClient client, String[] args) {
    String filePath = requiredArg(args, 1, "file path");
    String category = blankToDefault(optionalArg(args, 2), "Evidence");
    printJson(client.files().create(Path.of(filePath), category), false);
  }

  private static void handleDisputeAddEvidence(RyftClient client, String[] args) {
    String disputeId = requiredArg(args, 1, "dispute id");
    String fileId = requiredArg(args, 2, "file id");
    Map<String, Object> request = Map.of(
        "files", Map.of(
            "uncategorised", Map.of("id", fileId)
        )
    );
    printJson(client.disputes().addEvidence(disputeId, request), false);
  }

  private static void handleDisputeDeleteEvidence(RyftClient client, String[] args) {
    String disputeId = requiredArg(args, 1, "dispute id");
    Map<String, Object> request = Map.of("files", List.of("uncategorised"));
    printJson(client.disputes().deleteEvidence(disputeId, request), false);
  }

  private static void handlePaymentMethodUpdate(RyftClient client, String[] args) {
    String id = requiredArg(args, 1, "payment method id");
    Map<String, Object> request = Map.of("billingAddress", parseMap(requiredArg(args, 2, "billing address json")));
    printJson(client.paymentMethods().update(id, request), false);
  }

  private static void handleAccountLinkCreate(RyftClient client, String[] args) {
    Map<String, Object> request = Map.of(
        "accountId", requiredArg(args, 1, "account id"),
        "redirectUrl", requiredArg(args, 2, "redirect url")
    );
    printJson(client.accountLinks().generateTemporaryAccountLink(request), false);
  }

  private static void handleSubscriptionCreate(RyftClient client, String[] args) {
    String customerId = requiredArg(args, 1, "customer id");
    String paymentMethodId = requiredArg(args, 2, "payment method id");
    Map<String, Object> options = args.length > 3 && !args[3].isBlank() ? parseMap(args[3]) : new LinkedHashMap<>();
    Map<String, Object> request = buildSubscriptionCreateRequest(customerId, paymentMethodId, options);
    printJson(client.subscriptions().create(request), false);
  }

  private static void handleSubscriptionUpdate(RyftClient client, String[] args) {
    String id = requiredArg(args, 1, "subscription id");
    Map<String, Object> request = new LinkedHashMap<>();
    putIfNotBlank(request, "description", optionalArg(args, 2));
    if (args.length > 3 && !args[3].isBlank()) {
      request.put("metadata", parseMap(args[3]));
    }
    printJson(client.subscriptions().update(id, request), false);
  }

  private static void handleSubscriptionList(RyftClient client) {
    int startTimestamp = intFromEnv("RYFT_COLLECTION_START_TIMESTAMP");
    int endTimestamp = intFromEnv("RYFT_COLLECTION_END_TIMESTAMP");
    printJson(client.subscriptions().list(startTimestamp, endTimestamp, false, 10, ""), false);
  }

  private static void handleSubscriptionPaymentSessionList(RyftClient client, String[] args) {
    int startTimestamp = intFromEnv("RYFT_COLLECTION_START_TIMESTAMP");
    int endTimestamp = intFromEnv("RYFT_COLLECTION_END_TIMESTAMP");
    printJson(client.subscriptions().getPaymentSessions(requiredArg(args, 1, "subscription id"), startTimestamp, endTimestamp, false, 10, ""), false);
  }

  private static void handleSubscriptionPause(RyftClient client, String[] args) {
    Map<String, Object> request = new LinkedHashMap<>();
    putIfNotBlank(request, "reason", optionalArg(args, 2));
    if (args.length > 3 && !args[3].isBlank()) {
      request.put("resumeTimestamp", Integer.parseInt(args[3]));
    }
    if (args.length > 4 && !args[4].isBlank()) {
      request.put("unschedule", Boolean.parseBoolean(args[4]));
    }
    printJson(client.subscriptions().pause(requiredArg(args, 1, "subscription id"), request), false);
  }

  private static Map<String, Object> defaultAccountBody(String entityType, String email) {
    if (entityType.equalsIgnoreCase("Individual")) {
      return Map.of(
          "firstName", "Sdk",
          "lastName", "Individual",
          "email", email,
          "dateOfBirth", "1990-01-01",
          "gender", "Male",
          "nationalities", List.of("GB"),
          "address", defaultAddress()
      );
    }

    return Map.of(
        "name", "SDK Business " + System.currentTimeMillis(),
        "type", "PrivateCompany",
        "registrationNumber", "12345678",
        "registeredAddress", defaultAddress(),
        "contactEmail", email
    );
  }

  private static Map<String, Object> defaultAddress() {
    return Map.of(
        "lineOne", "1 SDK Street",
        "city", "London",
        "country", "GB",
        "postalCode", "SW1A1AA"
    );
  }

  private static Map<String, Object> parseMap(String rawJson) {
    return Json.MAPPER.convertValue(parseJson(rawJson), new TypeReference<>() {
    });
  }

  static AccountScopedRequest buildPaymentSessionCreateRequest(Map<String, Object> rawOptions) {
    AccountScopedRequest scoped = buildAccountScopedRequest(rawOptions);
    Map<String, Object> request = scoped.request();
    String customerId = stringValue(request.remove("customerId"));
    String previousPaymentId = stringValue(request.remove("previousPaymentId"));

    if (customerId != null) {
      request.put("customerDetails", Map.of("id", customerId));
    }
    if (previousPaymentId != null) {
      request.put("previousPayment", Map.of("id", previousPaymentId));
    }
    if (!request.containsKey("returnUrl")) {
      request.put("returnUrl", DEFAULT_RETURN_URL);
    }

    Object rawSplits = request.get("splits");
    if (rawSplits instanceof List<?>) {
      request.put("splits", Map.of("items", rawSplits));
    }

    return new AccountScopedRequest(request, scoped.accountId());
  }

  static Map<String, Object> buildSubscriptionCreateRequest(String customerId, String paymentMethodId, Map<String, Object> options) {
    Map<String, Object> request = new LinkedHashMap<>(options);
    request.put("customer", Map.of("id", customerId));
    if (!paymentMethodId.isBlank()) {
      request.put("paymentMethod", Map.of("id", paymentMethodId));
    }
    request.putIfAbsent("description", "SDK subscription readiness");
    if (!request.containsKey("price")) {
      request.put("price", Map.of(
          "amount", 100,
          "currency", "GBP",
          "interval", Map.of("unit", "Months", "count", 1, "times", 12)
      ));
    }
    return request;
  }

  static AccountScopedRequest buildAccountScopedRequest(Map<String, Object> rawOptions) {
    Map<String, Object> request = new LinkedHashMap<>(rawOptions);
    return new AccountScopedRequest(request, stringValue(request.remove("accountId")));
  }

  private static List<Object> parseList(String rawJson) {
    return Json.MAPPER.convertValue(parseJson(rawJson), new TypeReference<>() {
    });
  }

  record AccountScopedRequest(Map<String, Object> request, String accountId) {
  }

  private static JsonNode parseJson(String rawJson) {
    try {
      return Json.MAPPER.readTree(rawJson);
    } catch (IOException error) {
      throw new IllegalArgumentException("Failed to parse JSON: " + error.getMessage(), error);
    }
  }

  private static void printJson(Object value, boolean stderr) {
    try {
      String encoded = Json.MAPPER.writeValueAsString(value);
      if (stderr) {
        System.err.println(encoded);
      } else {
        System.out.println(encoded);
      }
    } catch (IOException error) {
      throw new IllegalStateException("Failed to encode JSON output", error);
    }
  }

  private static String requiredEnv(String name) {
    String value = System.getenv(name);
    if (value == null || value.isBlank()) {
      throw new IllegalArgumentException(name + " is required");
    }
    return value;
  }

  private static String requiredArg(String[] args, int index, String label) {
    if (args.length <= index || args[index] == null || args[index].isBlank()) {
      throw new IllegalArgumentException("missing required argument: " + label);
    }
    return args[index];
  }

  private static String optionalArg(String[] args, int index) {
    return args.length > index && args[index] != null && !args[index].isBlank() ? args[index] : null;
  }

  private static int parseIntArg(String[] args, int index, String label) {
    try {
      return Integer.parseInt(requiredArg(args, index, label));
    } catch (NumberFormatException error) {
      throw new IllegalArgumentException("invalid integer for " + label + ": " + args[index], error);
    }
  }

  private static Integer intOrNull(Object value) {
    if (value == null) {
      return null;
    }
    if (value instanceof Number number) {
      return number.intValue();
    }
    String text = value.toString();
    return text.isBlank() ? null : Integer.parseInt(text);
  }

  private static String stringOrNull(Object value) {
    if (value == null) {
      return null;
    }
    String text = value.toString();
    return text.isBlank() ? null : text;
  }

  private static Boolean boolOrNull(Object value) {
    if (value == null) {
      return null;
    }
    if (value instanceof Boolean bool) {
      return bool;
    }
    String text = value.toString();
    return text.isBlank() ? null : Boolean.parseBoolean(text);
  }

  private static void putIfNotBlank(Map<String, Object> target, String key, String value) {
    if (value != null && !value.isBlank()) {
      target.put(key, value);
    }
  }

  private static String blankToDefault(String value, String fallback) {
    return value == null || value.isBlank() ? fallback : value;
  }

  private static int intFromEnv(String name) {
    String value = System.getenv(name);
    if (value == null || value.isBlank()) {
      return 0;
    }
    return Integer.parseInt(value);
  }

  private static String stringValue(Object value) {
    if (value == null) {
      return null;
    }
    String stringValue = String.valueOf(value);
    return stringValue.isBlank() ? null : stringValue;
  }

  private static String requireValue(String value, String message) {
    if (value == null || value.isBlank()) {
      throw new IllegalArgumentException(message);
    }
    return value;
  }
}
