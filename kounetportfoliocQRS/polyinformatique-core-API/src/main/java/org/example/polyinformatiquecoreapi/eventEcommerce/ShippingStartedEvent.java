package org.example.polyinformatiquecoreapi.eventEcommerce;

import org.example.polyinformatiquecoreapi.event.BaseEvent;

public class ShippingStartedEvent extends BaseEvent<String> {
    public ShippingStartedEvent(String id) {
        super(id);
    }
}
