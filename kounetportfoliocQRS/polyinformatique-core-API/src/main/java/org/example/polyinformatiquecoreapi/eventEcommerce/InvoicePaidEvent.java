package org.example.polyinformatiquecoreapi.eventEcommerce;

import org.example.polyinformatiquecoreapi.event.BaseEvent;

public class InvoicePaidEvent extends BaseEvent<String> {
    public InvoicePaidEvent(String id) {
        super(id);
    }
}
