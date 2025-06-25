package org.example.polyinformatiquecoreapi.eventEcommerce;

import lombok.Getter;

@Getter
public class DeletedAddressEvent {
  private String id;

  public DeletedAddressEvent(String id) {
    this.id = id;
  }
}
