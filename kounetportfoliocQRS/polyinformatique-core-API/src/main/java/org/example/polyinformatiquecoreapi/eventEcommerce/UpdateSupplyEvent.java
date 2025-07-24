package org.example.polyinformatiquecoreapi.eventEcommerce;

import lombok.Getter;
import org.example.polyinformatiquecoreapi.dtoEcommerce.SupplierDTO;
import org.example.polyinformatiquecoreapi.event.BaseEvent;
@Getter
public class UpdateSupplyEvent extends BaseEvent<String> {
  private SupplierDTO supplier;

  protected UpdateSupplyEvent(String id, SupplierDTO supplier) {
    super(id);
    this.supplier = supplier;
  }
}
