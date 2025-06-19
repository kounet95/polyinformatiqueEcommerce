package org.example.polyinformatiquecoreapi.eventEcommerce;

import org.example.polyinformatiquecoreapi.event.BaseEvent;

public class SubcategoryDeletedEvent extends BaseEvent<String> {
  public SubcategoryDeletedEvent(String id) {
    super(id);
  }
}
