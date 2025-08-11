package org.example.polyinformatiquecoreapi.eventEcommerce;

import org.example.polyinformatiquecoreapi.dtoEcommerce.OrderDTO;
import org.example.polyinformatiquecoreapi.event.BaseEvent;
import java.util.List;

public class OrderConfirmedEvent extends BaseEvent<String> {

  private final OrderDTO orderDTO;

  public OrderConfirmedEvent(String id, OrderDTO orderDTO) {
    super(id);
    this.orderDTO = orderDTO;
  }

  public OrderDTO getOrderDTO() {
    return orderDTO;
  }
}
