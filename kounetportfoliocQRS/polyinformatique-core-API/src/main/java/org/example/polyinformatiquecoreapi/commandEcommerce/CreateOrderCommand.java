package org.example.polyinformatiquecoreapi.commandEcommerce;

import org.example.polyinformatiquecoreapi.dtoEcommerce.OrderDTO;

public class CreateOrderCommand extends BaseCommand<String> {
    private final OrderDTO orderDTO;
    private final boolean custom;
    public CreateOrderCommand(String id, OrderDTO orderDTO, boolean custom) {
        super(id);
        this.orderDTO = orderDTO;
      this.custom = custom;
    }

    public OrderDTO getOrderDTO() {
        return orderDTO;
    }
    public boolean isCustom() {
      return custom;
    }
}
