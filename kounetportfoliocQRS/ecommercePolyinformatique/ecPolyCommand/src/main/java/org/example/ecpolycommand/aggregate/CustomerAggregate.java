package org.example.ecpolycommand.aggregate;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.example.polyinformatiquecoreapi.commandEcommerce.CreateCustomerCommand;
import org.example.polyinformatiquecoreapi.commandEcommerce.CustomerUpdatedCommand;
import org.example.polyinformatiquecoreapi.commandEcommerce.DeleteCustomerCommand;
import org.example.polyinformatiquecoreapi.commandEcommerce.LinkAddressCommand;
import org.example.polyinformatiquecoreapi.dtoEcommerce.CustomerEcommerceDTO;
import org.example.polyinformatiquecoreapi.eventEcommerce.AddressLinkedEvent;
import org.example.polyinformatiquecoreapi.eventEcommerce.CustomerCreatedEvent;
import org.example.polyinformatiquecoreapi.eventEcommerce.CustomerDeletedEvent;
import org.example.polyinformatiquecoreapi.eventEcommerce.CustomerUpdatedEvent;

import java.util.UUID;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
@Slf4j
@Getter
@Setter
public class CustomerAggregate {

  @AggregateIdentifier
  private String customerId;
  private String firstName;
  private String lastName;
  private String email;
  private String phone;
  private String addressId;

  public CustomerAggregate() {}

  @CommandHandler
  public CustomerAggregate(CreateCustomerCommand cmd) {
    String id = cmd.getAuthor().getId();
    if (id == null || id.isEmpty()) {
      id = UUID.randomUUID().toString();
    }

    CustomerEcommerceDTO authorWithId = new CustomerEcommerceDTO(
      id,
      cmd.getAuthor().getFirstname(),
      cmd.getAuthor().getLastname(),
      cmd.getAuthor().getEmail(),
      cmd.getAuthor().getPhone(),
      cmd.getAuthor().getCreatedAt()
    );

    apply(new CustomerCreatedEvent(authorWithId));
  }

  @EventSourcingHandler
  public void on(CustomerCreatedEvent event) {
    this.customerId = event.getAuthor().getId();
    this.firstName = event.getAuthor().getFirstname();
    this.lastName = event.getAuthor().getLastname();
    this.email = event.getAuthor().getEmail();
    this.phone = event.getAuthor().getPhone();
  }

  @CommandHandler
  public void handle(CustomerUpdatedCommand command) {
    apply(new CustomerUpdatedEvent(command.getId(), command.getCustomerEcommerceDTO()));
  }

  @EventSourcingHandler
  public void on(CustomerUpdatedEvent event) {
    this.customerId = event.getId();
    this.firstName = event.getCustomerDTO().getFirstname();
    this.lastName = event.getCustomerDTO().getLastname();
    this.email = event.getCustomerDTO().getEmail();
    this.phone = event.getCustomerDTO().getPhone();
  }

  @CommandHandler
  public void handle(DeleteCustomerCommand command) {
    apply(new CustomerDeletedEvent(command.getId()));
  }

  @EventSourcingHandler
  public void on(CustomerDeletedEvent event) {
    this.firstName = "[deleted]";
    this.lastName = "[deleted]";
    this.email = null;
    this.phone = null;
    this.addressId = null;
  }

//  //cette methode pour gérer LinkAddressCommand aussi côté CustomerAggregate
//  @CommandHandler
//  public void handle(LinkAddressCommand cmd) {
//    if ("customer".equalsIgnoreCase(cmd.getTargetType()) && this.customerId.equals(cmd.getTargetId())) {
//      apply(new AddressLinkedEvent(cmd.getTargetType(), cmd.getTargetId(), cmd.getAddressId()));
//    }
//  }
//
//  @EventSourcingHandler
//  public void on(AddressLinkedEvent event) {
//    if ("customer".equalsIgnoreCase(event.getTargetType()) && this.customerId.equals(event.getTargetId())) {
//      this.addressId = event.getAddressId();
//    }
//  }
}
