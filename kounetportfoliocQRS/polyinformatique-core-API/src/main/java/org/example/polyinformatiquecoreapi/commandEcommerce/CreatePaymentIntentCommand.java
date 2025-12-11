package org.example.polyinformatiquecoreapi.commandEcommerce;

import lombok.AllArgsConstructor;
import lombok.Builder;
import org.example.polyinformatiquecoreapi.dtoEcommerce.OrderDTO;

@Builder
public class CreatePaymentIntentCommand extends BaseCommand<String> {
  private String orderId;
  private OrderDTO order;
  private long amountInCents;
  private String currency;

  protected CreatePaymentIntentCommand(String orderId, OrderDTO order, long amountInCents, String currency) {
    super(orderId);
    this.orderId = orderId;
    this.order = order;
    this.amountInCents = amountInCents;
    this.currency = currency;
  }


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

