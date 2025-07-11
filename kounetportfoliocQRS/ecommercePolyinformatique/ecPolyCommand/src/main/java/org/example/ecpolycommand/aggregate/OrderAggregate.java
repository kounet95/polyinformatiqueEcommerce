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

  public OrderAggregate() {
  }

  /**
   * Création de la commande
   */
  @CommandHandler
  public OrderAggregate(CreateOrderCommand cmd) {
    log.info("[AGGREGATE] CreateOrderCommand received: {}", cmd);
    apply(new OrderCreatedEvent(cmd.getId(), cmd.getOrderDTO()));
  }

  @CommandHandler
  public void handle(AddProductToOrderCommand cmd) {
    log.info("[AGGREGATE] AddProductToOrderCommand received: {}", cmd);
    apply(new ProductAddedToOrderEvent(cmd.getId(), cmd.getOrderLineDTO()));
  }

  @CommandHandler
  public void handle(ConfirmOrderCommand cmd) {
    if (this.confirmed) {
      throw new IllegalStateException("Order is already confirmed.");
    }
    if (this.orderLines.isEmpty()) {
      throw new IllegalStateException("Cannot confirm order without products.");
    }
    log.info("[AGGREGATE] ConfirmOrderCommand received: {}", cmd);
    apply(new OrderConfirmedEvent(cmd.getId()));
  }

  @CommandHandler
  public void handle(GenerateInvoiceCommand cmd) {
    if (!this.confirmed) {
      throw new IllegalStateException("Cannot generate invoice for unconfirmed order.");
    }
    if (this.paid) {
      throw new IllegalStateException("Invoice already paid.");
    }
    log.info("[AGGREGATE] GenerateInvoiceCommand received: {}", cmd);
    apply(new InvoiceGeneratedEvent(cmd.getId(), cmd.getInvoiceDTO()));
  }

  @CommandHandler
  public void handle(PayInvoiceCommand cmd) {
    if (!this.confirmed) {
      throw new IllegalStateException("Cannot pay invoice for unconfirmed order.");
    }
    if (this.paid) {
      throw new IllegalStateException("Invoice already paid.");
    }
    log.info("[AGGREGATE] PayInvoiceCommand received: {}", cmd);
    apply(new InvoicePaidEvent(cmd.getId()));
  }

  @CommandHandler
  public void handle(StartShippingCommand cmd) {
    if (!this.paid) {
      throw new IllegalStateException("Cannot start shipping unpaid order.");
    }
    log.info("[AGGREGATE] StartShippingCommand received: {}", cmd);
    apply(new ShippingStartedEvent(cmd.getId()));
  }

  @CommandHandler
  public void handle(DeliverOrderCommand cmd) {
    if (!this.shipped) {
      throw new IllegalStateException("Cannot deliver order that hasn't been shipped.");
    }
    log.info("[AGGREGATE] DeliverOrderCommand received: {}", cmd);
    apply(new OrderDeliveredEvent(cmd.getId()));
  }

  @CommandHandler
  public void handle(UpdateOrderStatusCommand cmd) {
    log.info("[AGGREGATE] UpdateOrderStatusCommand received: {}", cmd);
    apply(new OrderStatusUpdatedEvent(cmd.getId(), cmd.getBarcode(), cmd.getNewStatus()));
  }

  @CommandHandler
  public void handle(ScanOrderCommand cmd) {
    log.info("[AGGREGATE] ScanOrderCommand received: {}", cmd);
    apply(new OrderScannedEvent(cmd.getId(), cmd.getBarcode()));
  }

  @CommandHandler
  public void handle(CancelOrderCommand cmd) {
    if (this.delivered) {
      throw new IllegalStateException("Cannot cancel a delivered order.");
    }
    if (this.paid) {
      throw new IllegalStateException("Cannot cancel a paid order.");
    }
    log.info("[AGGREGATE] CancelOrderCommand received: {}", cmd);
    apply(new OrderCancelledEvent(cmd.getId(), cmd.getReason()));
  }

  // ---- Event sourcing ----

  @EventSourcingHandler
  public void on(OrderCreatedEvent event) {
    OrderDTO dto = event.getOrderDTO();
    this.orderId = dto.getId();
    this.customerId = dto.getCustomerId();
    this.supplierId = dto.getSupplierId();
    this.createdAt = dto.getCreatedAt();
    this.paymentMethod = dto.getPaymentMethod();
    this.total = dto.getTotal();
    this.orderStatus = OrderStatus.Inprogress;
    this.confirmed = false;
    this.paid = false;
    this.shipped = false;
    this.delivered = false;
    this.orderLines = new ArrayList<>();
    log.info("[AGGREGATE] Order created: {}", dto);
  }

  @EventSourcingHandler
  public void on(ProductAddedToOrderEvent event) {
    this.orderLines.add(event.getOrderLineDTO());
    log.info("[AGGREGATE] Product added: {}", event.getOrderLineDTO());
  }

  @EventSourcingHandler
  public void on(OrderConfirmedEvent event) {
    this.confirmed = true;
    this.orderStatus = OrderStatus.Inprogress;
    log.info("[AGGREGATE] Order confirmed.");
  }

  @EventSourcingHandler
  public void on(InvoiceGeneratedEvent event) {
    this.paymentMethod = event.getInvoiceDTO().getMethodePayment();
    this.total = event.getInvoiceDTO().getAmount();
    log.info("[AGGREGATE] Invoice generated: {}", event.getInvoiceDTO());
  }

  @EventSourcingHandler
  public void on(InvoicePaidEvent event) {
    this.paid = true;
    this.orderStatus = OrderStatus.Paid;
    log.info("[AGGREGATE] Invoice paid.");
  }

  @EventSourcingHandler
  public void on(ShippingStartedEvent event) {
    this.shipped = true;
    this.orderStatus = OrderStatus.Shipped;
    log.info("[AGGREGATE] Shipping started.");
  }

  @EventSourcingHandler
  public void on(OrderDeliveredEvent event) {
    this.delivered = true;
    this.orderStatus = OrderStatus.Delivered;
    log.info("[AGGREGATE] Order delivered.");
  }

  @EventSourcingHandler
  public void on(OrderStatusUpdatedEvent event) {
    this.orderStatus = event.getNewStatus();
    this.barcode = event.getBarcode();
    log.info("[AGGREGATE] Order status updated: {}", event.getNewStatus());
  }

  @EventSourcingHandler
  public void on(OrderScannedEvent event) {
    this.barcode = event.getBarcode();
    log.info("[AGGREGATE] Order scanned: {}", event.getBarcode());
  }

  @EventSourcingHandler
  public void on(OrderCancelledEvent event) {
    this.orderStatus = OrderStatus.Cancelled;
    log.info("[AGGREGATE] Order cancelled: {}", event.getReason());
  }
}
