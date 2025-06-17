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

import org.example.polyinformatiquecoreapi.dtoEcommerce.ProductSizeDTO;
import org.example.polyinformatiquecoreapi.eventEcommerce.ProductCreatedEvent;
import org.example.polyinformatiquecoreapi.eventEcommerce.ProductDeletedEvent;

import java.time.LocalDateTime;
import java.util.List;

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
    private LocalDateTime createdAt;
    private List<ProductSizeDTO> productSize;
    private String subcategoryId;
    private String socialGroupeId;
    private String modelsUrl;
    private Boolean isActive;

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
        this.createdAt = event.getProductDTO().getCreatedAt();
        this.productSize=event.getProductDTO().getProductSizes();
        this.subcategoryId = event.getProductDTO().getSubcategoryId();
        this.modelsUrl = event.getProductDTO().getModels();
    }

    @CommandHandler
    public void handle(DeleteProductCommand command) {
        apply(new ProductDeletedEvent(command.getId()));
    }

    @EventSourcingHandler
    public void on(ProductDeletedEvent event) {

        this.name = "[deleted]";
        this.description = "[deleted]";
        this.subcategoryId = "[deleted]";
        this.productId="[deleted]";
        this.modelsUrl = null;
        this.productSize=null;
        this.socialGroupeId="[deleted]";
        this.description = "[deleted]";
        this.createdAt = LocalDateTime.now();
    }
}
