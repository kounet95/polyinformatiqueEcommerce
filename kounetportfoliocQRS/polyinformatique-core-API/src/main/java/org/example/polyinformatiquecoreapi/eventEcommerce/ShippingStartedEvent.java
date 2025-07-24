package org.example.polyinformatiquecoreapi.eventEcommerce;

import lombok.Getter;
import org.example.polyinformatiquecoreapi.event.BaseEvent;
@Getter
public class ShippingStartedEvent extends BaseEvent<String> {
    public ShippingStartedEvent(String id) {
        super(id);
    }
}
