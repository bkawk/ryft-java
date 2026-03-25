package com.ryft.sdk;

import com.ryft.sdk.core.RyftConfig;
import com.ryft.sdk.core.RyftHttpClient;
import com.ryft.sdk.service.AccountLinksService;
import com.ryft.sdk.service.AccountsService;
import com.ryft.sdk.service.BalanceTransactionsService;
import com.ryft.sdk.service.BalancesService;
import com.ryft.sdk.service.CustomersService;
import com.ryft.sdk.service.DisputesService;
import com.ryft.sdk.service.EventsService;
import com.ryft.sdk.service.FilesService;
import com.ryft.sdk.service.PaymentMethodsService;
import com.ryft.sdk.service.PaymentSessionsService;
import com.ryft.sdk.service.PayoutMethodsService;
import com.ryft.sdk.service.PayoutsService;
import com.ryft.sdk.service.PersonsService;
import com.ryft.sdk.service.PlatformFeesService;
import com.ryft.sdk.service.SubscriptionsService;
import com.ryft.sdk.service.TransfersService;
import com.ryft.sdk.service.WebhooksService;

public final class RyftClient {
  private final RyftHttpClient httpClient;
  private final CustomersService customers;
  private final PaymentSessionsService paymentSessions;
  private final WebhooksService webhooks;
  private final AccountsService accounts;
  private final PersonsService persons;
  private final PayoutMethodsService payoutMethods;
  private final PayoutsService payouts;
  private final TransfersService transfers;
  private final BalancesService balances;
  private final BalanceTransactionsService balanceTransactions;
  private final PaymentMethodsService paymentMethods;
  private final AccountLinksService accountLinks;
  private final SubscriptionsService subscriptions;
  private final EventsService events;
  private final PlatformFeesService platformFees;
  private final FilesService files;
  private final DisputesService disputes;

  public RyftClient(String secretKey) {
    this(RyftConfig.builder(secretKey).build());
  }

  public RyftClient(RyftConfig config) {
    this.httpClient = new RyftHttpClient(config);
    this.customers = new CustomersService(httpClient);
    this.paymentSessions = new PaymentSessionsService(httpClient);
    this.webhooks = new WebhooksService(httpClient);
    this.accounts = new AccountsService(httpClient);
    this.persons = new PersonsService(httpClient);
    this.payoutMethods = new PayoutMethodsService(httpClient);
    this.payouts = new PayoutsService(httpClient);
    this.transfers = new TransfersService(httpClient);
    this.balances = new BalancesService(httpClient);
    this.balanceTransactions = new BalanceTransactionsService(httpClient);
    this.paymentMethods = new PaymentMethodsService(httpClient);
    this.accountLinks = new AccountLinksService(httpClient);
    this.subscriptions = new SubscriptionsService(httpClient);
    this.events = new EventsService(httpClient);
    this.platformFees = new PlatformFeesService(httpClient);
    this.files = new FilesService(httpClient);
    this.disputes = new DisputesService(httpClient);
  }

  public CustomersService customers() {
    return customers;
  }

  public PaymentSessionsService paymentSessions() {
    return paymentSessions;
  }

  public WebhooksService webhooks() {
    return webhooks;
  }

  public AccountsService accounts() {
    return accounts;
  }

  public PersonsService persons() {
    return persons;
  }

  public PayoutMethodsService payoutMethods() {
    return payoutMethods;
  }

  public PayoutsService payouts() {
    return payouts;
  }

  public TransfersService transfers() {
    return transfers;
  }

  public BalancesService balances() {
    return balances;
  }

  public BalanceTransactionsService balanceTransactions() {
    return balanceTransactions;
  }

  public PaymentMethodsService paymentMethods() {
    return paymentMethods;
  }

  public AccountLinksService accountLinks() {
    return accountLinks;
  }

  public SubscriptionsService subscriptions() {
    return subscriptions;
  }

  public EventsService events() {
    return events;
  }

  public PlatformFeesService platformFees() {
    return platformFees;
  }

  public FilesService files() {
    return files;
  }

  public DisputesService disputes() {
    return disputes;
  }
}
