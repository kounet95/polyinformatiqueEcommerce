package org.example.ecpolycommand.aggregate;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.example.polyinformatiquecoreapi.commandEcommerce.CreateCustomerCommand;
import org.example.polyinformatiquecoreapi.commandEcommerce.CustomerUpdatedCommand;
import org.example.polyinformatiquecoreapi.commandEcommerce.DeleteCustomerCommand;
import org.example.polyinformatiquecoreapi.commandEcommerce.LinkAddressCommand;
import org.example.polyinformatiquecoreapi.eventEcommerce.AddressLinkedEvent;
import org.example.polyinformatiquecoreapi.eventEcommerce.CustomerCreatedEvent;
import org.example.polyinformatiquecoreapi.eventEcommerce.CustomerDeletedEvent;
import org.example.polyinformatiquecoreapi.eventEcommerce.CustomerUpdatedEvent;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

/**
 * Customer Aggregate for handling customer-related commands
 */
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
         apply(new CustomerCreatedEvent(cmd.getAuthor()));
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
     public void handle(DeleteCustomerCommand command) {
         apply(new CustomerDeletedEvent(command.getId()));
     }

     @EventSourcingHandler
     public void on(CustomerDeletedEvent event) {

         this.firstName = "[deleted]";
         this.lastName = "[deleted]";
         this.email = null;
         this.addressId = null;
         this.phone = null;

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


}
