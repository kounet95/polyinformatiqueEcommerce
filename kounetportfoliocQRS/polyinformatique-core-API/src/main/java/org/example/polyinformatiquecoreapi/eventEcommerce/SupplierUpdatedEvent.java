package org.example.polyinformatiquecoreapi.eventEcommerce;

import org.example.polyinformatiquecoreapi.dtoEcommerce.SupplierDTO;
import org.example.polyinformatiquecoreapi.event.BaseEvent;

public class SupplierUpdatedEvent extends BaseEvent<String> {
  private SupplierDTO dto;
  protected SupplierUpdatedEvent(String id, SupplierDTO dto) {
    super(id);
    this.dto = dto;
  }

  public SupplierDTO getDto() {
    return dto;
  }
}
