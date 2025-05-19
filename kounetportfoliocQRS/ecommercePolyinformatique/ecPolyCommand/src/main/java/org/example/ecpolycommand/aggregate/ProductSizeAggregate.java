package org.example.ecpolycommand.aggregate;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.example.polyinformatiquecoreapi.commandEcommerce.CreateProductSizeCommand;
import org.example.polyinformatiquecoreapi.dtoEcommerce.ProductSizeDTO;
import org.example.polyinformatiquecoreapi.eventEcommerce.ProductSizeCreatedEvent;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

/**
 * ProductSize Aggregate for handling product size-related commands
 */
@Aggregate
@Slf4j
@Getter
@Setter
public class ProductSizeAggregate {

    @AggregateIdentifier
    private String productSizeId;
    private String productId;
    private String size;

    public ProductSizeAggregate() {}


     @CommandHandler
     public ProductSizeAggregate(CreateProductSizeCommand cmd) {
         apply(new ProductSizeCreatedEvent(cmd.getId(), cmd.getProductSizeDTO()));
     }


     @EventSourcingHandler
     public void on(ProductSizeCreatedEvent event) {
         this.productSizeId = event.getId();
         this.productId = event.getProductSizeDTO().getProductId();
         this.size = event.getProductSizeDTO().getSize();
     }
}
