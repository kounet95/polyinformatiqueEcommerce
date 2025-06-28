package org.example.ecpolycommand.aggregate;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.example.polyinformatiquecoreapi.commandEcommerce.*;
import org.example.polyinformatiquecoreapi.dtoEcommerce.CreatedAddressEvent;
import org.example.polyinformatiquecoreapi.eventEcommerce.*;

import java.util.UUID;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
@Slf4j
@Getter
@Setter
public class AddressAggregate {

  @AggregateIdentifier
  private String id;

  private String street;
  private String city;
  private String state;
  private String zip;
  private String country;
  private int appartment;

  // Liens des d'autres entités
  private String customer;
  private String store;
  private String supplier;
  private String shipping;

  public AddressAggregate() {
  }

  @CommandHandler
  public AddressAggregate(CreateAddressCommand cmd) {
    log.info("Creation de Address...");

    String id = cmd.getId();
    if (id == null || id.isBlank()) {
      id = UUID.randomUUID().toString();
    }
    apply(new CreatedAddressEvent(id, cmd.getAddressDTO()));
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
    log.info("Address created: {}", this.id);
  }

  @CommandHandler
  public void handle(DeleteAddressCommand cmd) {
    log.info("Deleting Address {}", cmd.getAddressId());
    apply(new DeletedAddressEvent(cmd.getAddressId()));
  }

  @EventSourcingHandler
  public void on(DeletedAddressEvent event) {
    this.id = "[deleted]";
    log.info("Address marked as deleted");
  }

  @CommandHandler
  public void handle(UpdateAddressCommand cmd) {
    log.info("Updating Address {}", cmd.getId());
    apply(new UpdatedAddressEvent(cmd.getId(), cmd.getDto()));
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
    log.info("Address updated: {}", this.id);
  }

  @CommandHandler
  public void handle(LinkAddressCommand cmd) {
    log.info("Linking Address {} to {} {}", cmd.getAddressId(), cmd.getTargetType(), cmd.getTargetId());

    if (!this.id.equals(cmd.getAddressId())) {
      throw new IllegalStateException("Trying to link to wrong aggregate instance");
    }

    apply(new AddressLinkedEvent(cmd.getTargetType(), cmd.getTargetId(), cmd.getAddressId()));
  }

  @EventSourcingHandler
  public void on(AddressLinkedEvent event) {
    switch (event.getTargetType().toLowerCase()) {
      case "customer" -> this.customer = event.getTargetId();
      case "store" -> this.store = event.getTargetId();
      case "supplier" -> this.supplier = event.getTargetId();
      case "shipping" -> this.shipping = event.getTargetId();
      default -> throw new IllegalArgumentException("Unknown target type: " + event.getTargetType());
    }
    log.info("Address {} linked to {} {}", this.id, event.getTargetType(), event.getTargetId());
  }
}
