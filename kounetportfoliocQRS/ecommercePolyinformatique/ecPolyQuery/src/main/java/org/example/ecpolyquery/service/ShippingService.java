package org.example.ecpolyquery.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.example.ecpolyquery.entity.Orderecommerce;
import org.example.ecpolyquery.entity.Shipping;
import org.example.ecpolyquery.repos.OrderecommerceRepository;
import org.example.ecpolyquery.repos.ShippingRepository;
import org.example.polyinformatiquecoreapi.dtoEcommerce.ShippingDTO;
import org.example.polyinformatiquecoreapi.eventEcommerce.ShippingStartedEvent;
import org.example.polyinformatiquecoreapi.eventEcommerce.ShippingUpdatedEvent;
import org.example.polyinformatiquecoreapi.eventEcommerce.ShippingDeletedEvent;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
@AllArgsConstructor
public class ShippingService {

  private final ShippingRepository shippingRepository;
  private final OrderecommerceRepository orderecommerceRepository;

  @EventHandler
  public void on(ShippingStartedEvent event) {
    log.debug("Handling ShippingStartedEvent: {}", event.getId());

    Orderecommerce order = orderecommerceRepository.findById(event.getId())
      .orElseThrow(() -> new RuntimeException("Order not found with id: " + event.getId()));

    String shippingAddress = (order.getCustomer() != null && order.getCustomer().getBillingAddress() != null)
      ? order.getCustomer().getBillingAddress()
      : "UNKNOWN_ADDRESS";

    // Check if shipping already exists for this order
    if (shippingRepository.existsById(event.getId())) {
      log.warn("Shipping already exists for order ID: {}", event.getId());
      return;
    }

    Shipping shipping = Shipping.builder()
      .id(event.getId())
      .deliveryStatus("SHIPPED")
      .shippingDate(LocalDateTime.now())
      .estimatedDeliveryDate(LocalDateTime.now().plusDays(5))
      .shippingAddress(shippingAddress)
      .orderecommerce(order)
      .build();

    shippingRepository.save(shipping);
    log.info("Shipping started for order ID: {}", order.getId());
  }

  @EventHandler
  public void on(ShippingUpdatedEvent event) {
    log.debug("Handling ShippingUpdatedEvent: {}", event.getId());
    ShippingDTO shippingDTO = event.getShippingDTO();
    Shipping shipping = shippingRepository.findById(event.getId())
      .orElseThrow(() -> new RuntimeException("Shipping not found with id: " + event.getId()));

    // Update fields - adapte selon tes champs
    shipping.setDeliveryStatus(shippingDTO.getDeliveryStatus());
    shipping.setShippingDate(shippingDTO.getShippingDate());
    shipping.setEstimatedDeliveryDate(shippingDTO.getEstimatedDeliveryDate());
    shipping.setShippingAddress(shippingDTO.getShippingAddress());
    // ... ajoute d'autres champs si besoin

    shippingRepository.save(shipping);
    log.info("Shipping updated for ID: {}", shipping.getId());
  }

  @EventHandler
  public void on(ShippingDeletedEvent event) {
    log.debug("Handling ShippingDeletedEvent: {}", event.getId());
    if (shippingRepository.existsById(event.getId())) {
      shippingRepository.deleteById(event.getId());
      log.info("Shipping deleted with ID: {}", event.getId());
    } else {
      log.warn("Tried to delete non-existent Shipping with ID: {}", event.getId());
    }
  }
}
