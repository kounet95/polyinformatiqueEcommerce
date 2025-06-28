package org.example.ecpolycommand.aggregate;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.example.polyinformatiquecoreapi.commandEcommerce.*;
import org.example.polyinformatiquecoreapi.dtoEcommerce.CreatedAddressEvent;
import org.example.polyinformatiquecoreapi.eventEcommerce.*;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
@Slf4j
@Getter
@Setter
public class AddressAggregate {
  @AggregateIdentifier
  private String  id;
  private String street;
  private String city;
  private String state;
  private String zip;
  private String country;
  private int appartment;
  private String customer;
  private String store;
  private String supplier;
  private String shipping;

  public AddressAggregate() {}

  @CommandHandler
  public AddressAggregate(CreateAddressCommand cmd) {
    // Apply the event with the address data
    apply(new CreatedAddressEvent(cmd.getId(), cmd.getAddressDTO()));
  }

  @EventSourcingHandler
  public void on(CreatedAddressEvent event) {
    this.id = event.getId();
    this.street = event.getAddressDTO().getStreet();
    this.city = event.getAddressDTO().getCity();
    this.state = event.getAddressDTO().getState();
    this.zip = event.getAddressDTO().getZip();
    this.country = event.getAddressDTO().getCountry();
    this.appartment = event.getAddressDTO().getAppartment();

  }

  @CommandHandler
  public void handle(DeleteAddressCommand command) {
    apply(new DeletedAddressEvent(command.getAddressId()));
  }

  @EventSourcingHandler
  public void on(DeletedAddressEvent event) {
    this.id = "[deleted]";
  }

  @CommandHandler
  public void handle(UpdateAddressCommand command) {
    apply(new UpdatedAddressEvent(command.getId(), command.getDto()));
  }

  @EventSourcingHandler
  public void on(UpdatedAddressEvent event) {
    this.id = event.getId();
    this.street = event.getAddressDTO().getStreet();
    this.city = event.getAddressDTO().getCity();
    this.state = event.getAddressDTO().getState();
    this.zip = event.getAddressDTO().getZip();
    this.country = event.getAddressDTO().getCountry();
    this.appartment = event.getAddressDTO().getAppartment();
  }

  @CommandHandler
  public void handle(LinkAddressCommand cmd) {
    // on doit mettre une logique métier eventuelle
    AggregateLifecycle.apply(new AddressLinkedEvent(cmd.getTargetType(),
      cmd.getTargetId(), cmd.getAddressId()));
  }


  @EventSourcingHandler
  public void on(AddressLinkedEvent event) {
    switch (event.getTargetType()) {
      case "customer" -> this.customer = event.getTargetId();
      case "store" -> this.store = event.getTargetId();
      case "supplier" -> this.supplier = event.getTargetId();
      case "shipping" -> this.shipping = event.getTargetId();
      default -> throw new IllegalArgumentException("Unknown target type: " + event.getTargetType());
    }
  }
}
