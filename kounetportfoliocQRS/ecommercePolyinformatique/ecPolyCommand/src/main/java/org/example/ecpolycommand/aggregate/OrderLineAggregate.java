package org.example.ecpolycommand.aggregate;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.example.polyinformatiquecoreapi.commandEcommerce.CreateOrderLineCommand;
import org.example.polyinformatiquecoreapi.commandEcommerce.DeleteOrderLineCommand;
import org.example.polyinformatiquecoreapi.eventEcommerce.OrderLineCreatedEvent;
import org.example.polyinformatiquecoreapi.eventEcommerce.OrderLineDeletedEvent;
import org.example.polyinformatiquecoreapi.dtoEcommerce.OrderLineDTO;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

/**
 * OrderLine Aggregate for handling order line-related commands
 */
@Aggregate
@Slf4j
@Getter
@Setter
public class OrderLineAggregate {

  @AggregateIdentifier
  private String orderLineId;
  private String orderId;
  private String productId;
  private int qty;
  private boolean deleted = false;

  public OrderLineAggregate() {}

  @CommandHandler
  public OrderLineAggregate(CreateOrderLineCommand cmd) {
    apply(new OrderLineCreatedEvent(cmd.getId(), cmd.getOrderLineDTO()));
  }

  @CommandHandler
  public void handle(DeleteOrderLineCommand cmd) {
    if (this.deleted) {
      throw new IllegalStateException("Order line already deleted!");
    }
    apply(new OrderLineDeletedEvent(cmd.getId()));
  }

  @EventSourcingHandler
  public void on(OrderLineCreatedEvent event) {
    OrderLineDTO dto = event.getOrderLineDTO();
    this.orderLineId = dto.getId();
    this.orderId = dto.getOrderId();
    this.productId = dto.getStockId();
    this.qty = dto.getQty();
    this.deleted = false;
  }

  @EventSourcingHandler
  public void on(OrderLineDeletedEvent event) {
    this.deleted = true;
  }
}
