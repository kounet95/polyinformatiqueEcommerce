package org.example.ecpolycommand.aggregate;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.example.polyinformatiquecoreapi.commandEcommerce.CreateProductSizeCommand;
import org.example.polyinformatiquecoreapi.commandEcommerce.DeleteProductSizeCommand;
import org.example.polyinformatiquecoreapi.dtoEcommerce.ProductSizeDTO;
import org.example.polyinformatiquecoreapi.eventEcommerce.ProductSizeCreatedEvent;
import org.example.polyinformatiquecoreapi.eventEcommerce.ProductSizeDeletedEvent;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

/**
 * Aggregate for handling ProductSize related commands and events.
 */
@Aggregate
@Slf4j
@Getter
@Setter
public class ProductSizeAggregate {

  @AggregateIdentifier
  private String id;
  private String prodId;
  private String imageUrl;
  private Double price;
  private Double pricePromo;
  private String sizeProd;
  private boolean deleted;

  public ProductSizeAggregate() {}

  @CommandHandler
  public ProductSizeAggregate(CreateProductSizeCommand cmd) {
    ProductSizeDTO dto = cmd.getProductSizeDTO();
    apply(new ProductSizeCreatedEvent(cmd.getId(), dto));
  }

  @EventSourcingHandler
  public void on(ProductSizeCreatedEvent event) {
    ProductSizeDTO dto = event.getProductSizeDTO();
    this.id = event.getId();
    this.prodId = dto.getProdId();
    this.imageUrl = dto.getImageUrl();
    this.price = dto.getPrice();
    this.pricePromo = dto.getPricePromo();
    this.sizeProd = dto.getSizeProd() != null ? dto.getSizeProd().name() : null;
    this.deleted = false;
  }

  @CommandHandler
  public void handle(DeleteProductSizeCommand cmd) {
    if (this.deleted) {
      throw new IllegalStateException("Product size already deleted.");
    }
    apply(new ProductSizeDeletedEvent(cmd.getId()));
  }

  @EventSourcingHandler
  public void on(ProductSizeDeletedEvent event) {
    this.deleted = true;
  }
}
