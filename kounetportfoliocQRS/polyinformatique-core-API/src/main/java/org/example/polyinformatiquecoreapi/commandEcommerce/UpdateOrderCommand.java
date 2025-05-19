package org.example.polyinformatiquecoreapi.commandEcommerce;

import org.example.polyinformatiquecoreapi.dtoEcommerce.OrderDTO;

public class UpdateOrderCommand extends BaseCommand<String> {
    private final OrderDTO orderDTO;

    public UpdateOrderCommand(String id, OrderDTO orderDTO) {
        super(id);
        this.orderDTO = orderDTO;
    }

    public OrderDTO getOrderDTO() {
        return orderDTO;
    }
}