package org.example.polyinformatiquecoreapi.commandEcommerce;

import org.example.polyinformatiquecoreapi.dtoEcommerce.OrderLineDTO;

public class CreateOrderLineCommand extends BaseCommand<String> {
    private OrderLineDTO orderLineDTO;

    public CreateOrderLineCommand(String id, OrderLineDTO orderLineDTO) {
        super(id);
        this.orderLineDTO = orderLineDTO;
    }

    public OrderLineDTO getOrderLineDTO() {
        return orderLineDTO;
    }
}