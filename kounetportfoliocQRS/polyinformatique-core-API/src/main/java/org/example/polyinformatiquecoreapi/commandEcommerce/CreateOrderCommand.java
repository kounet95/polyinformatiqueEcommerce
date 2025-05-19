package org.example.polyinformatiquecoreapi.commandEcommerce;

import org.example.polyinformatiquecoreapi.dtoEcommerce.OrderDTO;

public class CreateOrderCommand extends BaseCommand<String> {
    private final OrderDTO orderDTO;

    public CreateOrderCommand(String id, OrderDTO orderDTO) {
        super(id);
        this.orderDTO = orderDTO;
    }

    public OrderDTO getOrderDTO() {
        return orderDTO;
    }
}