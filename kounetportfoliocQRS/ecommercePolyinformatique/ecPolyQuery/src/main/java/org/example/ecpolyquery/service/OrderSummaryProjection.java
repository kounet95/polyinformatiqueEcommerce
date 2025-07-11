package org.example.ecpolyquery.service;


import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.example.ecpolyquery.query.FindOrderSummaryQuery;
import org.example.ecpolyquery.query.OrderSummary;
import org.example.polyinformatiquecoreapi.eventEcommerce.*;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class OrderSummaryProjection {

  private final Map<String, OrderSummary> store = new HashMap<>();

  @EventHandler
  public void on(OrderCreatedEvent event) {
    OrderSummary summary = new OrderSummary();
    summary.setOrderId(event.getOrderDTO().getId());
    summary.setCustomerId(event.getOrderDTO().getCustomerId());
    summary.setStatus("CREATED");
    store.put(summary.getOrderId(), summary);
    log.info("[Projection] Order created: {}", summary);
  }

  @EventHandler
  public void on(OrderConfirmedEvent event) {
    store.get(event.getId()).setStatus("CONFIRMED");
  }

  @EventHandler
  public void on(InvoiceGeneratedEvent event) {
    store.get(event.getId()).setStatus("INVOICE_GENERATED");
  }

  @EventHandler
  public void on(InvoicePaidEvent event) {
    store.get(event.getId()).setStatus("PAID");
  }

  @EventHandler
  public void on(ShippingStartedEvent event) {
    store.get(event.getId()).setStatus("SHIPPING");
  }

  @EventHandler
  public void on(OrderDeliveredEvent event) {
    store.get(event.getId()).setStatus("DELIVERED");
  }

  @EventHandler
  public void on(OrderCancelledEvent event) {
    store.get(event.getId()).setStatus("CANCELLED");
  }

  @QueryHandler
  public OrderSummary handle(FindOrderSummaryQuery query) {
    return store.get(query.getOrderId());
  }
}
