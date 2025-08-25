package org.example.ecpolycommand.saga;

import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.spring.stereotype.Saga;
import org.axonframework.modelling.saga.StartSaga;
import org.example.polyinformatiquecoreapi.commandEcommerce.*;
import org.example.polyinformatiquecoreapi.dtoEcommerce.InvoiceDTO;
import org.example.polyinformatiquecoreapi.eventEcommerce.*;

import java.util.UUID;

@Saga
@Slf4j
@NoArgsConstructor
public class OrderProcessingSaga {

  @Inject
  private transient CommandGateway commandGateway;

  @StartSaga
  @SagaEventHandler(associationProperty = "id")
  public void on(OrderCreatedEvent event) {
    String orderId = event.getId();
    SagaLifecycle.associateWith("id", orderId);

    log.info("[Saga] Start for Order {}", orderId);

    //Confirmer la commande
    commandGateway.send(new ConfirmOrderCommand(orderId));
    event.getOrderDTO().getOrderLines().forEach(line ->
      commandGateway.send(new DecreaseStockCommand(line.getStockId(), line.getQty()))
        .whenComplete((result, ex) -> {
          if(ex != null) {
            log.error("Erreur lors de la diminution de stock pour stockId={}", line.getStockId(), ex);
            //tu peux ici lancer une compensation, ou annuler la commande etc.
          }
        }));

    // Créer le paiement via Stripe (avec builder)
    commandGateway.send(CreatePaymentIntentCommand.builder()
      .orderId(orderId)
      .amountInCents(Math.round(event.getOrderDTO().getTotal() * 100))
      .currency(event.getOrderDTO().getCurrency())
      .order(event.getOrderDTO())
      .build());


    // Générer une facture
    InvoiceDTO invoice = new InvoiceDTO(
      UUID.randomUUID().toString(),
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

  @SagaEventHandler(associationProperty = "orderId")
  public void on(PaymentIntentCreatedEvent event) {
    log.info(" [Saga] PaymentIntent created for order {} with clientSecret={}",
      event.getOrderId(), event.getClientSecret());
    // TODO : notifier le frontend avec le clientSecret si nécessaire
  }

  @SagaEventHandler(associationProperty = "orderId")
  public void on(PaymentCompletedEvent event) {
    log.info("[Saga] Payment completed for order {}", event.getOrderId());
    commandGateway.send(new CompleteOrderCommand(event.getOrderId()));
    SagaLifecycle.end(); // Termine la saga proprement
  }

  @SagaEventHandler(associationProperty = "orderId")
  public void on(PaymentFailedEvent event) {
    log.warn(" [Saga] Payment failed for order {}", event.getOrderId());
    commandGateway.send(new CancelOrderCommand(event.getOrderId()));
    SagaLifecycle.end(); // Termine la saga proprement
  }
}
