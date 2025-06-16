package org.example.ecpolyquery.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.example.ecpolyquery.entity.OrderStatusView;
import org.example.ecpolyquery.entity.Orderecommerce;
import org.example.ecpolyquery.repos.CustomerRepository;
import org.example.ecpolyquery.repos.OrderStatusViewRepository;
import org.example.ecpolyquery.repos.OrderecommerceRepository;
import org.example.polyinformatiquecoreapi.eventEcommerce.OrderScannedEvent;
import org.example.polyinformatiquecoreapi.eventEcommerce.OrderStatusUpdatedEvent;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderStatusViewEventHandler {

  private final OrderStatusViewRepository orderStatusViewRepository;
  private final OrderecommerceRepository orderecommerceRepository;
  private final CustomerRepository customerRepository;

  @EventHandler
  public void on(OrderStatusUpdatedEvent event) {
    log.debug("Handling OrderStatusUpdatedEvent: {}", event.getId());

    Orderecommerce order = orderecommerceRepository.findById(event.getId())
      .orElseThrow(() -> new RuntimeException("Order not found with id: " + event.getId()));

    // Met à jour le statut et le code-barres dans la commande
    order.setOrderStatus(event.getNewStatus());
    order.setBarcode(event.getBarcode());
    orderecommerceRepository.save(order);

    // Met à jour ou crée la vue de statut
    Optional<OrderStatusView> optionalOrderStatus = orderStatusViewRepository.findByOrderId(event.getId());
    OrderStatusView orderStatusView;

    if (optionalOrderStatus.isPresent()) {
      orderStatusView = optionalOrderStatus.get();
      orderStatusView.setStatus(event.getNewStatus());
      orderStatusView.setBarcode(event.getBarcode());
      orderStatusView.setUpdatedAt(LocalDateTime.now());
    } else {
      orderStatusView = OrderStatusView.builder()
        .orderId(event.getId())
        .barcode(event.getBarcode())
        .status(event.getNewStatus())
        .updatedAt(LocalDateTime.now())
        .customer(order.getCustomer())
        .build();
    }

    orderStatusViewRepository.save(orderStatusView);
  }

  @EventHandler
  public void on(OrderScannedEvent event) {
    log.debug("Handling OrderScannedEvent: {}", event.getId());

    Orderecommerce order = orderecommerceRepository.findById(event.getId())
      .orElseThrow(() -> new RuntimeException("Order not found with id: " + event.getId()));

    //on met à jour le code-barres dans la commande
    order.setBarcode(event.getBarcode());
    orderecommerceRepository.save(order);

    // Met à jour ou crée la vue de statut
    Optional<OrderStatusView> optionalOrderStatus = orderStatusViewRepository.findByOrderId(event.getId());
    OrderStatusView orderStatusView;

    if (optionalOrderStatus.isPresent()) {
      orderStatusView = optionalOrderStatus.get();
      orderStatusView.setBarcode(event.getBarcode());
      orderStatusView.setUpdatedAt(LocalDateTime.now());
    } else {
      orderStatusView = OrderStatusView.builder()
        .orderId(event.getId())
        .barcode(event.getBarcode())
        .status(order.getOrderStatus())
        .updatedAt(LocalDateTime.now())
        .customer(order.getCustomer())
        .build();
    }

    orderStatusViewRepository.save(orderStatusView);
  }
}
