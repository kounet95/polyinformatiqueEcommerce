package org.example.polyinformatiquecoreapi.eventEcommerce;

import lombok.Getter;
import org.example.polyinformatiquecoreapi.dtoEcommerce.ShippingDTO;
import org.example.polyinformatiquecoreapi.event.BaseEvent;

/**
 * Event emitted when a shipping is created
 */
@Getter
public class ShippingCreatedEvent extends BaseEvent<String> {

    private final ShippingDTO shippingDTO;

    public ShippingCreatedEvent(String id, ShippingDTO shippingDTO) {
      super(id);

      this.shippingDTO = shippingDTO;
    }
}
