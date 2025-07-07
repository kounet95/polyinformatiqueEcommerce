package org.example.polyinformatiquecoreapi.eventEcommerce;

import lombok.Getter;

@Getter
public class ProductdeLikedEvent {
  private String id;
  public ProductdeLikedEvent(String id) {
    this.id = id;
  }
}
