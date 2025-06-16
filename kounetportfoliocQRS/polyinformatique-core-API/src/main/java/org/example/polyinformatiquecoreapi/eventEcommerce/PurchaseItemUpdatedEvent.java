package org.example.polyinformatiquecoreapi.eventEcommerce;

import org.example.polyinformatiquecoreapi.event.BaseEvent;

import java.io.Serializable;

public class PurchaseItemUpdatedEvent extends BaseEvent<String> implements Serializable {

  private PurchaseItemDTO dto;


  protected PurchaseItemUpdatedEvent(String id, PurchaseItemDTO dto) {
    super(id);
    this.dto = dto;
  }

  public PurchaseItemDTO getDto() {
    return dto;
  }
}
