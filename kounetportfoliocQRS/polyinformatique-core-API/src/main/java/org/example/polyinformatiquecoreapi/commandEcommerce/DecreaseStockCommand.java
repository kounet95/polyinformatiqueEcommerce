package org.example.polyinformatiquecoreapi.commandEcommerce;

import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
@Getter
public class DecreaseStockCommand {

  @TargetAggregateIdentifier
  private final String stockId;

  private final int quantity;

  public DecreaseStockCommand(String stockId, int quantity) {
    this.stockId = stockId;
    this.quantity = quantity;
  }

  public String getStockId() {
    return stockId;
  }

  public int getQuantity() {
    return quantity;
  }
}


