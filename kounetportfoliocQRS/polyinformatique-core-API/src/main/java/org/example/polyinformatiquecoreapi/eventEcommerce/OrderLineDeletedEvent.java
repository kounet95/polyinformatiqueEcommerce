package org.example.polyinformatiquecoreapi.eventEcommerce;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderLineDeletedEvent {

  private String idString;

  public OrderLineDeletedEvent(String idString) {
    this.idString = idString;
  }
}
