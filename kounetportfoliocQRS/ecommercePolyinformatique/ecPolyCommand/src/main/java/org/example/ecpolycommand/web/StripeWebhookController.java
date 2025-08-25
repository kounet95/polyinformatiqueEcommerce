package org.example.ecpolycommand.web;

import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import org.example.ecpolycommand.service.StripeService;
import org.example.polyinformatiquecoreapi.eventEcommerce.PaymentCompletedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stripe")
public class StripeWebhookController {

  private final ApplicationEventPublisher eventPublisher;
  private final StripeService stripeService;
  public StripeWebhookController(ApplicationEventPublisher eventPublisher, StripeService stripeService) {
    this.eventPublisher = eventPublisher;
    this.stripeService = stripeService;
  }

  @PostMapping("/webhook")
  public ResponseEntity<String> handleWebhook(@RequestBody String payload, @RequestHeader("Stripe-Signature") String sigHeader) {
    try {
      Event event = stripeService.constructEvent(payload, sigHeader);

      if ("payment_intent.succeeded".equals(event.getType())) {
        PaymentIntent paymentIntent = (PaymentIntent) event.getDataObjectDeserializer().getObject().orElse(null);

        if (paymentIntent != null) {
          String orderId = paymentIntent.getMetadata().get("orderId"); // Assure-toi d'avoir mis orderId en metadata lors de la création

          // Publie l'événement PaymentCompletedEvent pour la Saga
          eventPublisher.publishEvent(new PaymentCompletedEvent(orderId));
        }
      }
      return ResponseEntity.ok("");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid signature");
    }
  }
}

