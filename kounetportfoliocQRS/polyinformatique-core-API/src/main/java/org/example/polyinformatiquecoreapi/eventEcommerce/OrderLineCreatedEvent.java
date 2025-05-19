package org.example.polyinformatiquecoreapi.eventEcommerce;

import lombok.Getter;
import org.example.polyinformatiquecoreapi.dtoEcommerce.OrderLineDTO;

/**
 * Event emitted when an order line is created
 */
@Getter
public class OrderLineCreatedEvent {
    private final String id;
    private final OrderLineDTO orderLineDTO;

    public OrderLineCreatedEvent(String id, OrderLineDTO orderLineDTO) {
        this.id = id;
        this.orderLineDTO = orderLineDTO;
    }
}