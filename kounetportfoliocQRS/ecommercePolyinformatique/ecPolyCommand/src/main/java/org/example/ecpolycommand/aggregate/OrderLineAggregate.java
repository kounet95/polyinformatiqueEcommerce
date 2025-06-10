package org.example.ecpolycommand.aggregate;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.example.polyinformatiquecoreapi.commandEcommerce.CreateOrderLineCommand;

import org.example.polyinformatiquecoreapi.eventEcommerce.OrderLineCreatedEvent;

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

    public OrderLineAggregate() {}


     @CommandHandler
     public OrderLineAggregate(CreateOrderLineCommand cmd) {
         apply(new OrderLineCreatedEvent(cmd.getId(), cmd.getOrderLineDTO()));
     }


     @EventSourcingHandler
     public void on(OrderLineCreatedEvent event) {
         this.orderLineId = event.getId();
         this.orderId = event.getOrderLineDTO().getOrderId();
         this.productId = event.getOrderLineDTO().getProductId();
         this.qty = event.getOrderLineDTO().getQty();
     }
}
