package org.example.polyinformatiquecoreapi.eventEcommerce;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.polyinformatiquecoreapi.dtoEcommerce.OrderLineDTO;
@AllArgsConstructor
@Getter
public class OrderLineUpdatedEvent {

  private OrderLineDTO orderLineDTO;

}
