package org.example.polyinformatiquecoreapi.eventEcommerce;

import org.example.polyinformatiquecoreapi.event.BaseEvent;

public class SupplierDeletedEvent extends BaseEvent<String> {

  protected SupplierDeletedEvent(String id) {
    super(id);
  }
}
