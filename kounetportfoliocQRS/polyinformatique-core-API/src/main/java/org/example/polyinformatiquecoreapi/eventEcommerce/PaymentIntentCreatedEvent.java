package org.example.polyinformatiquecoreapi.eventEcommerce;

public class PaymentIntentCreatedEvent {

  private final String orderId;
  private final String clientSecret;

  public PaymentIntentCreatedEvent(String orderId, String clientSecret) {
    this.orderId = orderId;
    this.clientSecret = clientSecret;
  }

  public String getOrderId() {
    return orderId;
  }

  public String getClientSecret() {
    return clientSecret;
  }
}

