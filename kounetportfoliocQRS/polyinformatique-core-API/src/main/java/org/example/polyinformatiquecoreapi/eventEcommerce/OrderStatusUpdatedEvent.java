package org.example.polyinformatiquecoreapi.eventEcommerce;

import lombok.Getter;
import org.example.polyinformatiquecoreapi.event.BaseEvent;

@Getter
public class OrderStatusUpdatedEvent extends BaseEvent<String> {
    private final String barcode;
    private final String newStatus;

    public OrderStatusUpdatedEvent(String id, String barcode, String newStatus) {
        super(id);
        this.barcode = barcode;
        this.newStatus = newStatus;
    }
}