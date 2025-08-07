package org.example.ecpolycommand.saga;

import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.spring.stereotype.Saga;
import org.axonframework.modelling.saga.StartSaga;
import org.example.polyinformatiquecoreapi.commandEcommerce.*;
import org.example.polyinformatiquecoreapi.dtoEcommerce.InvoiceDTO;
import org.example.polyinformatiquecoreapi.eventEcommerce.*;

@Slf4j
@Saga
public class OrderManagementSaga {

  @Inject
  private transient CommandGateway commandGateway;

  private String orderId;

  @StartSaga
  @SagaEventHandler(associationProperty = "id")
  public void handle(OrderCreatedEvent event) {
    this.orderId = event.getOrderDTO().getId();
    SagaLifecycle.associateWith("id", this.orderId);

    log.info("[Saga] ✅ Saga started for Order ID: {}", orderId);

    // 1. Confirmer la commande
    commandGateway.send(new ConfirmOrderCommand(orderId));

    // 2. Générer une facture
    InvoiceDTO invoice = new InvoiceDTO(
      "", // invoiceId (sera généré)
      orderId,
      event.getOrderDTO().getCustomerEmail(),
      event.getOrderDTO().getTotal(),
      event.getOrderDTO().getPaymentMethod(),
      0.0,
      "WAITING",
      event.getOrderDTO().getSupplierId()
    );

    commandGateway.send(new GenerateInvoiceCommand(orderId, invoice));
  }

  @SagaEventHandler(associationProperty = "id")
  public void handle(InvoiceGeneratedEvent event) {
    log.info("[Saga] 🧾 Invoice generated for Order ID: {}", orderId);
    // Aucune commande à envoyer ici car le paiement est fait côté client via Stripe.
    // On attend donc l’événement InvoicePaidEvent.
  }

  @SagaEventHandler(associationProperty = "id")
  public void handle(InvoicePaidEvent event) {
    log.info("[Saga] 💳 Invoice paid for Order ID: {}", orderId);
    // Déclenche l’expédition après paiement
    commandGateway.send(new StartShippingCommand(orderId));
  }

  @SagaEventHandler(associationProperty = "id")
  public void handle(ShippingStartedEvent event) {
    log.info("[Saga] 🚚 Shipping started for Order ID: {}", orderId);
    // Optionnel : notification, email, etc.
  }

  @SagaEventHandler(associationProperty = "id")
  public void handle(OrderDeliveredEvent event) {
    log.info("[Saga] 📦 Order delivered, ending saga for Order ID: {}", orderId);
    SagaLifecycle.end();
  }

  @SagaEventHandler(associationProperty = "id")
  public void handle(OrderCancelledEvent event) {
    log.info("[Saga] ❌ Order cancelled, ending saga for Order ID: {}", orderId);
    SagaLifecycle.end();
  }
}
