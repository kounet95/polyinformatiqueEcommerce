package org.example.polyinformatiquecoreapi.commandEcommerce;

import org.example.polyinformatiquecoreapi.dtoEcommerce.OrderLineDTO;

public class AddProductToOrderCommand extends BaseCommand<String> {
    private final OrderLineDTO orderLineDTO;

    public AddProductToOrderCommand(String id, OrderLineDTO orderLineDTO) {
        super(id);
        this.orderLineDTO = orderLineDTO;
    }

    public OrderLineDTO getOrderLineDTO() {
        return orderLineDTO;
    }
}