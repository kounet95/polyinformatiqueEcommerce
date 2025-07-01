package org.example.ecpolycommand.aggregate;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.example.polyinformatiquecoreapi.commandEcommerce.AddStockCommand;
import org.example.polyinformatiquecoreapi.commandEcommerce.LinkAddressCommand;
import org.example.polyinformatiquecoreapi.eventEcommerce.AddressLinkedEvent;
import org.example.polyinformatiquecoreapi.eventEcommerce.StockDecreasedEvent;
import org.example.polyinformatiquecoreapi.eventEcommerce.StockIncreasedEvent;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
@Slf4j
@Getter
@Setter
public class StockAggregate {

  @AggregateIdentifier
  private String id;
  private String productSizeId;
  private String supplierId;
  private Double purchasePrice;
  private Double promoPrice;
  private double quantity;
private String supplyId;
  public StockAggregate() {}

  @CommandHandler
  public StockAggregate(AddStockCommand cmd) {
    apply(new StockIncreasedEvent(cmd.getId(), cmd.getStockDTO()));
  }

  @EventSourcingHandler
  public void on(StockIncreasedEvent event) {
    this.id = event.getId();
    this.productSizeId = event.getStockDTO().getProductSizeId();
    this.supplierId = event.getStockDTO().getSupplierId();
    this.purchasePrice = event.getStockDTO().getPurchasePrice();
    this.promoPrice = event.getStockDTO().getPromoPrice();
    this.quantity += event.getStockDTO().getQuantity();
    this.supplyId = event.getStockDTO().getSupplyId();

  }

  @EventSourcingHandler
  public void on(StockDecreasedEvent event) {
    this.quantity -= event.getStockDTO().getQuantity();
  }


}
