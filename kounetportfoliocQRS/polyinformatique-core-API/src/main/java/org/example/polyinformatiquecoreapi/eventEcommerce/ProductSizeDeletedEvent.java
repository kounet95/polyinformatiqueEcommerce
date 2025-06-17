package org.example.polyinformatiquecoreapi.eventEcommerce;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProductSizeDeletedEvent {
  private String id;

  public ProductSizeDeletedEvent(String id) {
    this.id = id;
  }
}
