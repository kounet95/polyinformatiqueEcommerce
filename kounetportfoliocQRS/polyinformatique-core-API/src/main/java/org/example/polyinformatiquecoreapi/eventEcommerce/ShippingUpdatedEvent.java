package org.example.polyinformatiquecoreapi.eventEcommerce;

import lombok.Getter;
import org.example.polyinformatiquecoreapi.dtoEcommerce.ShippingDTO;
import org.example.polyinformatiquecoreapi.event.BaseEvent;
@Getter
public class ShippingUpdatedEvent extends BaseEvent<String> {

  private ShippingDTO shippingDTO;
  protected ShippingUpdatedEvent(String id, ShippingDTO shippingDTO) {
    super(id);
    this.shippingDTO = shippingDTO;
  }
}
