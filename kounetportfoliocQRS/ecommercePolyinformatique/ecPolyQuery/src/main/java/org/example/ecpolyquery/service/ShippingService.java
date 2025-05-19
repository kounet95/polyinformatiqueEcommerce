package org.example.ecpolyquery.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.example.ecpolyquery.entity.Orderecommerce;
import org.example.ecpolyquery.entity.Shipping;
import org.example.ecpolyquery.repos.OrderecommerceRepository;
import org.example.ecpolyquery.repos.ShippingRepository;
import org.example.polyinformatiquecoreapi.eventEcommerce.ShippingStartedEvent;
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
        
        // Get shipping address from customer
        String shippingAddress = order.getCustomer().getShippingAddress();
        
        // Create shipping with default values
        Shipping shipping = Shipping.builder()
                .id(event.getId())
                .deliveryStatus("SHIPPED")
                .shippingDate(LocalDateTime.now())
                .estimatedDeliveryDate(LocalDateTime.now().plusDays(5)) // Default to 5 days delivery
                .shippingAddress(shippingAddress)
                .orderecommerce(order)
                .build();
        
        shippingRepository.save(shipping);
        log.info("Shipping started for order ID: {}", order.getId());
    }
}