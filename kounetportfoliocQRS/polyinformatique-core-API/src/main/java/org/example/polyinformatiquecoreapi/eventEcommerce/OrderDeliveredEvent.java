package org.example.polyinformatiquecoreapi.eventEcommerce;

import org.example.polyinformatiquecoreapi.event.BaseEvent;

public class OrderDeliveredEvent extends BaseEvent<String> {
    public OrderDeliveredEvent(String id) {
        super(id);
    }
}

