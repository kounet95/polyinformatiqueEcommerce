package org.example.polyinformatiquecoreapi.commandEcommerce;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@AllArgsConstructor
@Getter
public class CompleteOrderCommand {

  @TargetAggregateIdentifier
  private final String orderId;


}
