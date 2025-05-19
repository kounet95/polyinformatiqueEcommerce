package org.example.polyinformatiquecoreapi.eventEcommerce;

import org.example.polyinformatiquecoreapi.dtoEcommerce.OrderDTO;
import org.example.polyinformatiquecoreapi.event.BaseEvent;

public class OrderCreatedEvent extends BaseEvent<String> {
    private final OrderDTO orderDTO;

    public OrderCreatedEvent(String id, OrderDTO orderDTO) {
        super(id);
        this.orderDTO = orderDTO;
    }

    public OrderDTO getOrderDTO() {
        return orderDTO;
    }
}

