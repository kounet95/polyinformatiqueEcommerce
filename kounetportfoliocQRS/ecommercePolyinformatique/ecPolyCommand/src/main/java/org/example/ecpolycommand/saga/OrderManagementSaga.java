package org.example.ecpolycommand.saga;

import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.spring.stereotype.Saga;
import org.example.polyinformatiquecoreapi.commandEcommerce.*;
import org.example.polyinformatiquecoreapi.dtoEcommerce.InvoiceDTO;
import org.example.polyinformatiquecoreapi.dtoEcommerce.OrderDTO;
import org.example.polyinformatiquecoreapi.eventEcommerce.*;

@Slf4j
@Saga
public class OrderManagementSaga {

  @Inject
  private transient CommandGateway commandGateway;

  private String orderId;

  @SagaEventHandler(associationProperty = "id")
  public void handle(OrderCreatedEvent event) {
    this.orderId = event.getOrderDTO().getId();
    log.info("[Saga] Order created: {}", orderId);



    commandGateway.send(new ConfirmOrderCommand(orderId));


    InvoiceDTO invoice = new InvoiceDTO(
      "",
      orderId,
      event.getOrderDTO().getCustomerId(),
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
    log.info("[Saga] Invoice generated for order {}", orderId);
    // Ici, en pratique le paiement se déclenche côté front via Stripe
    // Donc rien à faire ici, on attend l’événement InvoicePaidEvent
  }

  @SagaEventHandler(associationProperty = "id")
  public void handle(InvoicePaidEvent event) {
    log.info("[Saga] Invoice paid for order {}", orderId);
    //Déclencher l’expédition
    commandGateway.send(new StartShippingCommand(orderId));
  }

  @SagaEventHandler(associationProperty = "id")
  public void handle(ShippingStartedEvent event) {
    log.info("[Saga] Shipping started for order {}", orderId);
    //Dans un vrai flux on va envoyer une notification, etc.
  }

  @SagaEventHandler(associationProperty = "id")
  public void handle(OrderDeliveredEvent event) {
    log.info("[Saga] Order delivered, ending saga for {}", orderId);
    SagaLifecycle.end(); //  Fin de la saga après livraison
  }

  @SagaEventHandler(associationProperty = "id")
  public void handle(OrderCancelledEvent event) {
    log.info("[Saga] Order cancelled, ending saga for {}", orderId);
    SagaLifecycle.end(); //  Fin si la commande est annulée
  }
}

