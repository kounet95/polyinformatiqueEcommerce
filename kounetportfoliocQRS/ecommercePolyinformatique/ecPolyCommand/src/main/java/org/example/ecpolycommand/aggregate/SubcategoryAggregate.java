package org.example.ecpolycommand.aggregate;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.example.polyinformatiquecoreapi.commandEcommerce.CreateSubcategoryCommand;
import org.example.polyinformatiquecoreapi.eventEcommerce.SubcategoryCreatedEvent;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

/**
 * Subcategory Aggregate for handling subcategory-related commands
 */
@Aggregate
@Slf4j
@Getter
@Setter
public class SubcategoryAggregate {

    @AggregateIdentifier
    private String subcategoryId;
    private String name;
    private String categoryId;

    public SubcategoryAggregate() {}


     @CommandHandler
     public SubcategoryAggregate(CreateSubcategoryCommand cmd) {
         apply(new SubcategoryCreatedEvent(cmd.getId(), cmd.getSubcategoryDTO()));
     }


     @EventSourcingHandler
     public void on(SubcategoryCreatedEvent event) {
         this.subcategoryId = event.getId();
         this.name = event.getSubcategoryDTO().getName();
         this.categoryId = event.getSubcategoryDTO().getCategoryId();
     }
}