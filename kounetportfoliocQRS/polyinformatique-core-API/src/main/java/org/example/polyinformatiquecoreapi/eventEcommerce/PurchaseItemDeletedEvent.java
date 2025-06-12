package org.example.polyinformatiquecoreapi.eventEcommerce;

import org.example.polyinformatiquecoreapi.event.BaseEvent;

public class PurchaseItemDeletedEvent extends BaseEvent<String> {

  protected PurchaseItemDeletedEvent(String id) {
    super(id);
  }
}
