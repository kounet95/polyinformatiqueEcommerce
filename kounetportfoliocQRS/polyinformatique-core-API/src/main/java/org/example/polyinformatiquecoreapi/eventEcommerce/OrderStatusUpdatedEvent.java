package org.example.polyinformatiquecoreapi.eventEcommerce;

import lombok.Getter;
import org.example.polyinformatiquecoreapi.dtoEcommerce.OrderStatus;
import org.example.polyinformatiquecoreapi.event.BaseEvent;

@Getter
public class OrderStatusUpdatedEvent extends BaseEvent<String> {
    private final String barcode;
    private final OrderStatus newStatus;

    public OrderStatusUpdatedEvent(String id, String barcode, OrderStatus newStatus) {
        super(id);
        this.barcode = barcode;
        this.newStatus = newStatus;
    }
}
