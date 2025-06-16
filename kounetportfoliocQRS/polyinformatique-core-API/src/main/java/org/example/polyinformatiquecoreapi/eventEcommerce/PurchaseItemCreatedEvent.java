package org.example.polyinformatiquecoreapi.eventEcommerce;

import org.example.polyinformatiquecoreapi.event.BaseEvent;

import java.io.Serializable;

public class PurchaseItemCreatedEvent extends BaseEvent<String> implements Serializable {
  private PurchaseItemDTO purchaseItemDTO;

  public PurchaseItemCreatedEvent(String id, PurchaseItemDTO purchaseItemDTO) {
    super(id);
    this.purchaseItemDTO = purchaseItemDTO;
  }

  public PurchaseItemDTO getPurchaseItemDTO() {
    return purchaseItemDTO;
  }
}
