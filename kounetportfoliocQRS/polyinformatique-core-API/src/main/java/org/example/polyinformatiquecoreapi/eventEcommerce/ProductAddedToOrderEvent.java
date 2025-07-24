package org.example.polyinformatiquecoreapi.eventEcommerce;

import org.example.polyinformatiquecoreapi.dtoEcommerce.OrderLineDTO;
import org.example.polyinformatiquecoreapi.event.BaseEvent;

public class ProductAddedToOrderEvent extends BaseEvent<String> {
    private final OrderLineDTO orderLineDTO;

    public ProductAddedToOrderEvent(String id, OrderLineDTO orderLineDTO) {
        super(id);
        this.orderLineDTO = orderLineDTO;
    }

    public OrderLineDTO getOrderLineDTO() {
        return orderLineDTO;
    }
}