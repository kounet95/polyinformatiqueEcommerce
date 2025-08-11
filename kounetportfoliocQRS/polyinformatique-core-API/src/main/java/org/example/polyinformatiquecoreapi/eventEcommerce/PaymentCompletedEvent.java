package org.example.polyinformatiquecoreapi.eventEcommerce;

public class PaymentCompletedEvent {

  private final String orderId;

  public PaymentCompletedEvent(String orderId) {
    this.orderId = orderId;
  }

  public String getOrderId() {
    return orderId;
  }
}

