package org.example.ecpolycommand.web;

import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.PaymentIntent;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.example.ecpolycommand.service.StripeService;
import org.example.polyinformatiquecoreapi.commandEcommerce.PayInvoiceCommand;
import org.example.polyinformatiquecoreapi.dtoEcommerce.InvoiceDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

  private final StripeService stripeService;
  private final CommandGateway commandGateway;


  public PaymentController(StripeService stripeService, CommandGateway commandGateway) {
    this.stripeService = stripeService;
    this.commandGateway = commandGateway;
  }

  /**
   * 🔵 Crée une session Stripe Checkout
   */
  @PostMapping("/create-checkout-session")
  public ResponseEntity<Map<String, String>> createCheckoutSession(@RequestBody InvoiceDTO invoiceDTO)
    throws StripeException {

    String sessionId = stripeService.createCheckoutSession(invoiceDTO);

    Map<String, String> response = new HashMap<>();
    response.put("sessionId", sessionId);

    return ResponseEntity.ok(response);
  }

  /**
   *  Webhook Stripe
   */
  @PostMapping("/webhook")
  public ResponseEntity<String> handleWebhook(
    @RequestBody String payload,
    @RequestHeader("Stripe-Signature") String sigHeader
  ) throws Exception {

    Event event = stripeService.constructEvent(payload, sigHeader);

    if ("checkout.session.completed".equals(event.getType())) {
      var dataObjectDeserializer = event.getDataObjectDeserializer();
      if (dataObjectDeserializer.getObject().isPresent()) {
        Session session = (Session) dataObjectDeserializer.getObject().get();
        String orderId = session.getClientReferenceId();
        String paymentIntentId = session.getPaymentIntent();

        //  Ici tu dois avoir l'InvoiceDTO.
        // Soit tu le retrouves en base avec l'orderId, soit tu passes null si ton CommandHandler le recharge.
        InvoiceDTO invoiceDTO = new InvoiceDTO(); //  FAKE ! Remplace par un vrai findByOrderId(orderId)

        commandGateway.send(new PayInvoiceCommand(orderId, invoiceDTO, paymentIntentId, session.getId()));
      }
    }

    return ResponseEntity.ok("Webhook Stripe reçu");
  }

}
