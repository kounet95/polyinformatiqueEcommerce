package org.example.ecpolycommand.aggregate;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.example.polyinformatiquecoreapi.commandEcommerce.CreateShippingCommand;
import org.example.polyinformatiquecoreapi.commandEcommerce.DeleteShippingCommand;
import org.example.polyinformatiquecoreapi.commandEcommerce.LinkAddressCommand;
import org.example.polyinformatiquecoreapi.dtoEcommerce.OrderStatus;
import org.example.polyinformatiquecoreapi.dtoEcommerce.ShippingDTO;
import org.example.polyinformatiquecoreapi.eventEcommerce.AddressLinkedEvent;
import org.example.polyinformatiquecoreapi.eventEcommerce.ShippingCreatedEvent;
import org.example.polyinformatiquecoreapi.eventEcommerce.ShippingDeletedEvent;

import java.time.LocalDateTime;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
@Slf4j
@Getter
@Setter
public class ShippingAggregate {

  @AggregateIdentifier
  private String shippingId;
  private String orderId;
  private String address;
  private OrderStatus orderStatus;
  private LocalDateTime estimatedDeliveryDate;
  private LocalDateTime shippingDate;
  private LocalDateTime createdAt;
  private boolean deleted;

  public ShippingAggregate() {}

  @CommandHandler
  public ShippingAggregate(CreateShippingCommand cmd) {
    apply(new ShippingCreatedEvent(cmd.getId(), cmd.getShippingDTO()));
  }

  @CommandHandler
  public void handle(DeleteShippingCommand cmd) {
    if (this.deleted) {
      throw new IllegalStateException("Shipping already deleted.");
    }
    apply(new ShippingDeletedEvent(cmd.getId()));
  }

  @EventSourcingHandler
  public void on(ShippingCreatedEvent event) {
    ShippingDTO dto = event.getShippingDTO();
    this.shippingId = event.getId();
    this.orderId = dto.getOrderId();
    this.orderStatus = dto.getOrderStatus();
    this.estimatedDeliveryDate = dto.getEstimatedDeliveryDate();
    this.shippingDate = dto.getShippingDate();
    this.createdAt = dto.getCreatedAt();
    this.deleted = false;
  }

  @EventSourcingHandler
  public void on(ShippingDeletedEvent event) {
    this.deleted = true;
  }

  @CommandHandler
  public void handle(LinkAddressCommand cmd) {
    // on doit mettre une logique métier eventuelle
    AggregateLifecycle.apply(new AddressLinkedEvent(cmd.getTargetType(),
      cmd.getTargetId(), cmd.getAddressId()));
  }
}
