package org.example.ecpolycommand.aggregate;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.example.polyinformatiquecoreapi.commandEcommerce.CreateShippingCommand;
import org.example.polyinformatiquecoreapi.dtoEcommerce.ShippingDTO;
import org.example.polyinformatiquecoreapi.eventEcommerce.ShippingCreatedEvent;

import java.time.LocalDateTime;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

/**
 * Shipping Aggregate for handling shipping-related commands
 */
@Aggregate
@Slf4j
@Getter
@Setter
public class ShippingAggregate {

  @AggregateIdentifier

  private String shippingId;
  private String orderId;
  private String address;
  private String status;
  private LocalDateTime estimatedDeliveryDate;
  private LocalDateTime shippingDate;
  private LocalDateTime createdAt;

  public ShippingAggregate() {}

  @CommandHandler
  public ShippingAggregate(CreateShippingCommand cmd) {
    apply(new ShippingCreatedEvent(cmd.getId(), cmd.getShippingDTO()));
  }

  @EventSourcingHandler
  public void on(ShippingCreatedEvent event) {
    ShippingDTO dto = event.getShippingDTO();
    this.shippingId = event.getId();
    this.orderId = dto.getOrderId();
    this.address = dto.getShippingAddress();
    this.status = dto.getDeliveryStatus();
    this.estimatedDeliveryDate = dto.getEstimatedDeliveryDate();
    this.shippingDate = dto.getShippingDate();
    this.createdAt = dto.getCreatedAt();
  }
}
