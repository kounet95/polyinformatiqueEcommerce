package org.example.ecpolycommand.aggregate;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.example.polyinformatiquecoreapi.commandEcommerce.CreateProductCommand;
import org.example.polyinformatiquecoreapi.commandEcommerce.DeleteProductCommand;
import org.example.polyinformatiquecoreapi.dtoEcommerce.ProductDTO;
import org.example.polyinformatiquecoreapi.eventEcommerce.ProductCreatedEvent;
import org.example.polyinformatiquecoreapi.eventEcommerce.ProductDeletedEvent;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

/**
 * Product Aggregate for handling product-related commands
 */
@Aggregate
@Slf4j
@Getter
@Setter
public class ProductAggregate {

    @AggregateIdentifier
    private String productId;
    private String name;
    private String description;
    private double price;
    private String subcategoryId;
    private String imageUrl;

    public ProductAggregate() {}

    @CommandHandler
    public ProductAggregate(CreateProductCommand cmd) {
        apply(new ProductCreatedEvent(cmd.getId(), cmd.getProductDTO()));
    }

    @EventSourcingHandler
    public void on(ProductCreatedEvent event) {
        this.productId = event.getId();
        this.name = event.getProductDTO().getName();
        this.description = event.getProductDTO().getDescription();
        this.price = event.getProductDTO().getPrice();
        this.subcategoryId = event.getProductDTO().getSubcategoryId();
        this.imageUrl = event.getProductDTO().getImageUrl();
    }

    @CommandHandler
    public void handle(DeleteProductCommand command) {
        apply(new ProductDeletedEvent(command.getId()));
    }

    @EventSourcingHandler
    public void on(ProductDeletedEvent event) {
        // Mark the product as deleted or perform any necessary cleanup
        this.name = "[deleted]";
        this.description = "[deleted]";
        this.imageUrl = null;
    }
}
