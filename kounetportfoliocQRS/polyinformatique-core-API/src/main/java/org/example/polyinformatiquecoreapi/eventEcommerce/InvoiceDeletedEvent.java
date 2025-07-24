package org.example.polyinformatiquecoreapi.eventEcommerce;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class InvoiceDeletedEvent {
  private final String id;

  public InvoiceDeletedEvent(String id) {
    this.id = id;
  }
}
