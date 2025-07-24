package org.example.ecpolycommand.aggregate;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.example.polyinformatiquecoreapi.commandEcommerce.CreateSupplierCommand;
import org.example.polyinformatiquecoreapi.commandEcommerce.LinkAddressCommand;
import org.example.polyinformatiquecoreapi.dtoEcommerce.SupplierDTO;
import org.example.polyinformatiquecoreapi.eventEcommerce.AddressLinkedEvent;
import org.example.polyinformatiquecoreapi.eventEcommerce.SupplierCreatedEvent;
import org.example.polyinformatiquecoreapi.eventEcommerce.SupplierDeletedEvent;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

/**
 * Supplier Aggregate for handling supplier-related commands
 */
@Aggregate
@Slf4j
@Getter
@Setter
public class SupplierAggregate {

    @AggregateIdentifier
    private String supplierId;
    private String firstName;
    private String email;
    private String personToContact;
    private String  addressId;
     private boolean deleted;
    public SupplierAggregate() {}


     @CommandHandler
     public SupplierAggregate(CreateSupplierCommand cmd) {
         apply(new SupplierCreatedEvent(cmd.getId(), cmd.getSupplierDTO()));
     }


     @EventSourcingHandler
     public void on(SupplierCreatedEvent event) {
         this.supplierId = event.getId();
         this.firstName = event.getSupplierDTO().getFullname();
         this.email = event.getSupplierDTO().getEmail();
         this.personToContact = event.getSupplierDTO().getPersonToContact();
     }

  @EventSourcingHandler
  public void on(SupplierDeletedEvent event) {
    this.deleted = true;
  }


}
