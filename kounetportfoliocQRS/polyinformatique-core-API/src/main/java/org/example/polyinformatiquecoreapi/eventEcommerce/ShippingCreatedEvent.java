package org.example.polyinformatiquecoreapi.eventEcommerce;

import lombok.Getter;
import org.example.polyinformatiquecoreapi.dtoEcommerce.ShippingDTO;

/**
 * Event emitted when a shipping is created
 */
@Getter
public class ShippingCreatedEvent {
    private final String id;
    private final ShippingDTO shippingDTO;

    public ShippingCreatedEvent(String id, ShippingDTO shippingDTO) {
        this.id = id;
        this.shippingDTO = shippingDTO;
    }
}