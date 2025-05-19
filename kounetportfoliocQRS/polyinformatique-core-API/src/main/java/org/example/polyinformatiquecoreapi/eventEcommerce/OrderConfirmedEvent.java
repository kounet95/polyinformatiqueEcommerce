package org.example.polyinformatiquecoreapi.eventEcommerce;

import org.example.polyinformatiquecoreapi.event.BaseEvent;

public class OrderConfirmedEvent extends BaseEvent<String> {
    public OrderConfirmedEvent(String id) {
        super(id);
    }
}

