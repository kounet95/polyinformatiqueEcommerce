package org.example.ecpolyquery.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.example.ecpolyquery.entity.Customer;
import org.example.ecpolyquery.entity.Orderecommerce;
import org.example.ecpolyquery.repos.CustomerRepository;
import org.example.ecpolyquery.repos.OrderLineRepository;
import org.example.ecpolyquery.repos.OrderecommerceRepository;
import org.example.ecpolyquery.repos.ProductRepository;

import org.example.polyinformatiquecoreapi.dtoEcommerce.OrderDTO;
import org.example.polyinformatiquecoreapi.eventEcommerce.OrderCancelledEvent;
import org.example.polyinformatiquecoreapi.eventEcommerce.OrderConfirmedEvent;
import org.example.polyinformatiquecoreapi.eventEcommerce.OrderCreatedEvent;
import org.example.polyinformatiquecoreapi.eventEcommerce.OrderDeliveredEvent;
import org.example.polyinformatiquecoreapi.eventEcommerce.ProductAddedToOrderEvent;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderService {

    private final OrderecommerceRepository orderecommerceRepository;
    private final OrderLineRepository orderLineRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    @EventHandler
    public void on(OrderCreatedEvent event) {
        log.debug("Handling OrderCreatedEvent: {}", event.getId());
        OrderDTO orderDTO = event.getOrderDTO();

        Customer customer = customerRepository.findById(orderDTO.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + orderDTO.getCustomerId()));

        Orderecommerce order = Orderecommerce.builder()
                .id(orderDTO.getId())
                .createdAt(LocalDateTime.parse(orderDTO.getCreatedAt(), DateTimeFormatter.ISO_DATE_TIME))
                .orderStatus(orderDTO.getOrderStatus())
                .paymentMethod(orderDTO.getPaymentMethod())
                .total(orderDTO.getTotal())
                .customer(customer)
                .lines(new ArrayList<>())
                .build();

        orderecommerceRepository.save(order);
    }

    @EventHandler
    public void on(OrderConfirmedEvent event) {
        log.debug("Handling OrderConfirmedEvent: {}", event.getId());

        Orderecommerce order = orderecommerceRepository.findById(event.getId())
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + event.getId()));

        order.setOrderStatus("CONFIRMED");
        orderecommerceRepository.save(order);
    }

    @EventHandler
    public void on(OrderDeliveredEvent event) {
        log.debug("Handling OrderDeliveredEvent: {}", event.getId());

        Orderecommerce order = orderecommerceRepository.findById(event.getId())
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + event.getId()));

        order.setOrderStatus("DELIVERED");
        orderecommerceRepository.save(order);
    }

    @EventHandler
    public void on(ProductAddedToOrderEvent event) {
        // Implementation would depend on the structure of ProductAddedToOrderEvent
        // This is a placeholder implementation
        log.debug("Handling ProductAddedToOrderEvent: {}", event.getId());

        // Actual implementation would be added once we examine the ProductAddedToOrderEvent class
    }

    @EventHandler
    public void on(OrderCancelledEvent event) {
        log.debug("Handling OrderCancelledEvent: {}", event.getId());

        Orderecommerce order = orderecommerceRepository.findById(event.getId())
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + event.getId()));

        order.setOrderStatus("CANCELLED");
        orderecommerceRepository.save(order);
        log.info("Order cancelled with ID: {}, reason: {}", event.getId(), event.getReason());
    }
}
