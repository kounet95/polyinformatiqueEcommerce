package org.example.ecpolycommand.aggregate;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.example.polyinformatiquecoreapi.commandEcommerce.*;
import org.example.polyinformatiquecoreapi.dtoEcommerce.*;
import org.example.polyinformatiquecoreapi.eventEcommerce.*;

import java.util.ArrayList;
import java.util.List;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
@Slf4j
@Getter
@Setter
public class OrderAggregate {

  @AggregateIdentifier
  private String orderId;
  private String customerId;
  private String supplierId;
  private String createdAt;
  private OrderStatus orderStatus;
  private String paymentMethod;
  private double total;
  private String barcode;

  private boolean confirmed;
  private boolean paid;
  private boolean shipped;
  private boolean delivered;

  private List<OrderLineDTO> orderLines = new ArrayList<>();

  public OrderAggregate() {}

  @CommandHandler
  public OrderAggregate(CreateOrderCommand cmd) {
    /**       annalyse sur la creation dune commande   **/
    apply(
      new OrderCreatedEvent(
        cmd.getId(), cmd.getOrderDTO()));
  }

  @CommandHandler
  public void handle(AddProductToOrderCommand cmd) {
    apply(
      new ProductAddedToOrderEvent(cmd.getId(),
      cmd.getOrderLineDTO()));
  }

  @CommandHandler
  public void handle(ConfirmOrderCommand cmd) {

    apply(new OrderConfirmedEvent(cmd.getId()));
  }

  @CommandHandler
  public void handle(GenerateInvoiceCommand cmd) {
    apply(new InvoiceGeneratedEvent(cmd.getId(), cmd.getInvoiceDTO()));
  }

  @CommandHandler
  public void handle(PayInvoiceCommand cmd) {
    apply(new InvoicePaidEvent(cmd.getId()));
  }

  @CommandHandler
  public void handle(StartShippingCommand cmd) {
    apply(new ShippingStartedEvent(cmd.getId()));
  }

  @CommandHandler
  public void handle(DeliverOrderCommand cmd) {
    apply(new OrderDeliveredEvent(cmd.getId()));
  }

  @CommandHandler
  public void handle(UpdateOrderStatusCommand cmd) {
    apply(new OrderStatusUpdatedEvent(cmd.getId(), cmd.getBarcode(), cmd.getNewStatus()));
  }

  @CommandHandler
  public void handle(ScanOrderCommand cmd) {
    apply(new OrderScannedEvent(cmd.getId(), cmd.getBarcode()));
  }

  @CommandHandler
  public void handle(CancelOrderCommand command) {
    if (this.delivered) {
      throw new IllegalStateException("Cannot cancel an order that has already been delivered.");
    }
    apply(new OrderCancelledEvent(command.getId(), command.getReason()));
  }

  @EventSourcingHandler
  public void on(OrderCreatedEvent event) {
    OrderDTO dto = event.getOrderDTO();
    this.orderId = dto.getId();
    this.customerId = dto.getCustomerId();
    this.supplierId = dto.getSupplierId();
    this.createdAt = dto.getCreatedAt();

    this.paymentMethod = dto.getPaymentMethod();
    this.total = dto.getTotal();
    this.confirmed = false;
    this.paid = false;
    this.shipped = false;
    this.delivered = false;
    this.orderLines = new ArrayList<>();
  }

  @EventSourcingHandler
  public void on(ProductAddedToOrderEvent event) {
    this.orderLines.add(event.getOrderLineDTO());
  }

  @EventSourcingHandler
  public void on(OrderConfirmedEvent event) {
    this.confirmed = true;
  }

  @EventSourcingHandler
  public void on(InvoiceGeneratedEvent event) {
    // Update the order with invoice information
    InvoiceDTO invoiceDTO = event.getInvoiceDTO();
    // We don't update orderId, customerId, or supplierId as they are already set
    // and shouldn't change when an invoice is generated

    // Update payment-related information
    this.paymentMethod = invoiceDTO.getMethodePayment();
    this.total = invoiceDTO.getAmount();

    // Set order status to indicate invoice has been generated
    this.orderStatus = OrderStatus.Inprogress;

    // Log the invoice generation
    log.info("Invoice generated for order: {}", this.orderId);
  }

  @EventSourcingHandler
  public void on(InvoicePaidEvent event) {
    this.paid = true;
  }

  @EventSourcingHandler
  public void on(ShippingStartedEvent event) {
    this.shipped = true;
  }

  @EventSourcingHandler
  public void on(OrderDeliveredEvent event) {
    this.delivered = true;
  }

  @EventSourcingHandler
  public void on(OrderStatusUpdatedEvent event) {
    this.orderStatus = event.getNewStatus();
    this.barcode = event.getBarcode();
  }

  @EventSourcingHandler
  public void on(OrderScannedEvent event) {
    this.barcode = event.getBarcode();
  }

  @EventSourcingHandler
  public void on(OrderCancelledEvent event) {
    this.orderStatus = OrderStatus.Cancelled;
  }
}
