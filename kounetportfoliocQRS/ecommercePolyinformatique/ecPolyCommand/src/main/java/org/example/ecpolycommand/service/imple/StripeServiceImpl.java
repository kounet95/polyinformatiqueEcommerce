package org.example.ecpolycommand.service.imple;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.model.Event;
import com.stripe.net.Webhook;
import com.stripe.param.checkout.SessionCreateParams;
import org.example.ecpolycommand.service.StripeService;
import org.example.polyinformatiquecoreapi.dtoEcommerce.InvoiceDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StripeServiceImpl implements StripeService {

  @Value("${stripe.api.key}")
  private String stripeApiKey;

  @Value("${stripe.webhook.secret}")
  private String webhookSecret;

  @Override
  public String createCheckoutSession(InvoiceDTO invoice) throws StripeException {
    Stripe.apiKey = stripeApiKey;

    SessionCreateParams params =
      SessionCreateParams.builder()
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

  @Override
  public Event constructEvent(String payload, String sigHeader) throws Exception {
    Stripe.apiKey = stripeApiKey;
    return Webhook.constructEvent(payload, sigHeader, webhookSecret);
  }
}
