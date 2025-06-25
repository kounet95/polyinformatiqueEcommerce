package org.example.ecpolycommand.aggregate;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.example.polyinformatiquecoreapi.commandEcommerce.CreateAddressCommand;
import org.example.polyinformatiquecoreapi.commandEcommerce.DeleteAddressCommand;
import org.example.polyinformatiquecoreapi.commandEcommerce.DeleteCategoryCommand;
import org.example.polyinformatiquecoreapi.dtoEcommerce.CreatedAddressEvent;
import org.example.polyinformatiquecoreapi.eventEcommerce.CategoryCreatedEvent;
import org.example.polyinformatiquecoreapi.eventEcommerce.CategoryDeletedEvent;

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
            this.customer = event.getAddressDTO().getCustomer();
            this.store = event.getAddressDTO().getStore();
            this.supplier = event.getAddressDTO().getSupplier();
            this.shipping = event.getAddressDTO().getShipping();
          }

          @CommandHandler
          public void handle(DeleteAddressCommand command) {
            apply(new CategoryDeletedEvent(command.getAddressId()));
          }

          @EventSourcingHandler
          public void on(CategoryDeletedEvent event) {
            this.id = "[deleted]";
          }

}
