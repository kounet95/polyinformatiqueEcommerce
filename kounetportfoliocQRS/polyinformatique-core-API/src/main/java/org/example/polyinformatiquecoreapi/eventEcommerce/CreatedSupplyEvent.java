package org.example.polyinformatiquecoreapi.eventEcommerce;

import lombok.Getter;
import org.example.polyinformatiquecoreapi.dtoEcommerce.SupplyDTO;
import org.example.polyinformatiquecoreapi.event.BaseEvent;
@Getter
public class CreatedSupplyEvent extends BaseEvent<String> {
  private SupplyDTO supplyDTO;

  public CreatedSupplyEvent(String id, SupplyDTO supplyDTO) {
    super(id);
    this.supplyDTO = supplyDTO;
  }
}
