package org.example.ecpolycommand.service.imple;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.example.ecpolycommand.config.StripeConfigProperties;
import org.example.ecpolycommand.service.StripeService;
import org.example.polyinformatiquecoreapi.dtoEcommerce.InvoiceDTO;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StripeServiceImpl implements StripeService {

  private final StripeConfigProperties config;

  /**
   * Crée une session Checkout pour redirection Stripe.
   */
  @Override
  public String createCheckoutSession(InvoiceDTO invoice) throws StripeException {
    Stripe.apiKey = config.getApi().getKey();

    SessionCreateParams params = SessionCreateParams.builder()
      .setMode(SessionCreateParams.Mode.PAYMENT)
      .setCustomerEmail(invoice.getCustumerEmal())
      .setClientReferenceId(invoice.getId())
      .addLineItem(
        SessionCreateParams.LineItem.builder()
          .setQuantity(1L)
          .setPriceData(
            SessionCreateParams.LineItem.PriceData.builder()
              .setCurrency("eur")
              .setUnitAmount((long) (invoice.getAmount() * 100))
              .setProductData(
                SessionCreateParams.LineItem.PriceData.ProductData.builder()
                  .setName("Paiement Commande " + invoice.getId())
                  .build()
              )
              .build()
          )
          .build()
      )
      .setSuccessUrl("https://tonsite.com/success?session_id={CHECKOUT_SESSION_ID}")
      .setCancelUrl("https://tonsite.com/cancel")
      .build();

    Session session = Session.create(params);
    return session.getId();
  }

  /**
   * Construit un event webhook vérifié.
   */
  @Override
  public Event constructEvent(String payload, String sigHeader) throws Exception {
    Stripe.apiKey = config.getApi().getKey();
    return Webhook.constructEvent(payload, sigHeader, config.getWebhook().getSecret());
  }



  /**
   * Crée un PaymentIntent (API directe).
   * Montant doit déjà être en centimes.
   */
  @Override
  public PaymentIntent createPaymentIntent(long amountInCents, String currency) throws StripeException {
    Stripe.apiKey = config.getApi().getKey();

    return PaymentIntent.create(
      PaymentIntentCreateParams.builder()
        .setAmount(amountInCents)
        .setCurrency(currency)
        .build()
    );
  }



  @PostConstruct
  public void logStripeKey() {
    System.out.println("Stripe API Key loaded from config: " +
      (config.getApi() == null || config.getApi().getKey() == null ? "<null>" : config.getApi().getKey().substring(0, 6) + "********"));
    System.out.println("Stripe Webhook secret loaded: " +
      (config.getWebhook() == null || config.getWebhook().getSecret() == null ? "<null>" : config.getWebhook().getSecret().substring(0, 6) + "********"));
  }
}
