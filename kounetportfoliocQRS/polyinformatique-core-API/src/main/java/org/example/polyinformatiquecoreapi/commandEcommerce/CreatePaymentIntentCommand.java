package org.example.polyinformatiquecoreapi.commandEcommerce;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.example.polyinformatiquecoreapi.dtoEcommerce.OrderDTO;
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreatePaymentIntentCommand {
  @TargetAggregateIdentifier
  private  String orderId;
  private OrderDTO order;
  private  long amountInCents;
  private String currency;

//  public CreatePaymentIntentCommand(String orderId, long amountInCents, String currency, OrderDTO order) {
//    this.orderId = orderId;
//    this.amountInCents = amountInCents;
//    this.currency = currency;
//    this.order = order;
//  }

  public String getOrderId() { return orderId; }
  public long getAmountInCents() { return amountInCents; }
  public String getCurrency() { return currency; }
  public OrderDTO getOrder() { return order; }
}

