package org.example.ecpolycommand.aggregate;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.example.polyinformatiquecoreapi.commandEcommerce.CreateCategoryCommand;
import org.example.polyinformatiquecoreapi.commandEcommerce.DeleteCategoryCommand;
import org.example.polyinformatiquecoreapi.eventEcommerce.CategoryCreatedEvent;
import org.example.polyinformatiquecoreapi.eventEcommerce.CategoryDeletedEvent;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

/**
 * Category Aggregate for handling category-related commands
 */
@Aggregate
@Slf4j
@Getter
@Setter
public class CategoryAggregate {

    @AggregateIdentifier
    private String categoryId;
    private String name;

    public CategoryAggregate() {}


     @CommandHandler
     public CategoryAggregate(CreateCategoryCommand cmd) {


         apply(new
           CategoryCreatedEvent(cmd.getId(), cmd.getCategoryDTO()));

     }


     @EventSourcingHandler
     public void on(CategoryCreatedEvent event) {
         this.categoryId = event.getId();
         this.name = event.getCategoryDTO().getName();
     }

     @CommandHandler
     public void handle(DeleteCategoryCommand command) {
         apply(new CategoryDeletedEvent(command.getId()));
     }

     @EventSourcingHandler
     public void on(CategoryDeletedEvent event) {
         this.name = "[deleted]";
     }
 }
