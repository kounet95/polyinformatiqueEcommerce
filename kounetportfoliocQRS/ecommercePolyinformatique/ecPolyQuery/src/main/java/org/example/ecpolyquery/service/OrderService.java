package org.example.ecpolyquery.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.example.ecpolyquery.entity.*;
import org.example.ecpolyquery.repos.*;

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
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderService {

    private final OrderecommerceRepository orderecommerceRepository;
    private final OrderLineRepository orderLineRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final SupplierRepository supplierRepository;
    private final InvoiceRepository invoiceRepository;
    private final  StockRepository stockRepository;

  @EventHandler
    public void on(OrderCreatedEvent event) {
        log.debug("Handling OrderCreatedEvent: {}", event.getId());
        OrderDTO orderDTO = event.getOrderDTO();

        Customer customer = customerRepository.findById(orderDTO.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + orderDTO.getCustomerId()));
           Supplier supplier = supplierRepository.findById(orderDTO.getSupplierId())
                .orElseThrow(() -> new RuntimeException("supplier not found with id: " + orderDTO.getSupplierId()));


           Orderecommerce order = Orderecommerce.builder()
          .id(orderDTO.getId())
          .createdAt(LocalDateTime.parse(orderDTO.getCreatedAt(), DateTimeFormatter.ISO_DATE_TIME))
             .paymentMethod(orderDTO.getPaymentMethod())
          .orderStatus(OrderStatus.Inprogress)

          .customer(customer)
          .supplierId(supplier)

          .build();

        orderecommerceRepository.save(order);
    }

    @EventHandler
    public void on(OrderConfirmedEvent event) {
        log.debug("Handling OrderConfirmedEvent: {}", event.getId());

        Orderecommerce order = orderecommerceRepository.findById(event.getId())
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + event.getId()));

        order.setOrderStatus(OrderStatus.Delivered);
        orderecommerceRepository.save(order);
    }

    @EventHandler
    public void on(OrderDeliveredEvent event) {
        log.debug("Handling OrderDeliveredEvent: {}", event.getId());

        Orderecommerce order = orderecommerceRepository.findById(event.getId())
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + event.getId()));

        order.setOrderStatus(OrderStatus.Delivered);
        orderecommerceRepository.save(order);
    }

  @EventHandler
  public void on(ProductAddedToOrderEvent event) {
    log.debug("Handling ProductAddedToOrderEvent: {}", event.getId());

    // 1. Récupérer la commande
    Orderecommerce order = orderecommerceRepository.findById(event.getOrderLineDTO().getOrderId())
      .orElseThrow(() -> new RuntimeException("Order not found with id: " + event.getOrderLineDTO().getOrderId()));

    // 2. Vérifier le statut
    if (order.getOrderStatus() == OrderStatus.Delivered || order.getOrderStatus() == OrderStatus.Cancelled) {
      throw new IllegalStateException("Cannot add product: order already " + order.getOrderStatus());
    }

    // 3. Récupérer le Stock et remonter jusqu'au Product
    Stock stock = stockRepository.findById(event.getOrderLineDTO().getStockId())
      .orElseThrow(() -> new RuntimeException("Stock not found with id: " + event.getOrderLineDTO().getStockId()));

    Product product = stock.getProductSize().getProductId();
    if (product == null) {
      throw new RuntimeException("No Product linked to Stock: " + stock.getId());
    }

    // 4. Créer OrderLine
    OrderLine orderLine = OrderLine.builder()
      .id(UUID.randomUUID().toString())
      .orderecommerce(order)
      .stockId(stock)
      .qty(event.getOrderLineDTO().getQty())
      .build();

    // 5. Sauvegarder
    order.getOrderLines().add(orderLine);
    orderLineRepository.save(orderLine);
    orderecommerceRepository.save(order);

    log.info("Product {} (qty: {}) added to order {}", product.getId(), event.getOrderLineDTO().getQty(), order.getId());
  }


  @EventHandler
    public void on(OrderCancelledEvent event) {
        log.debug("Handling OrderCancelledEvent: {}", event.getId());

        Orderecommerce order = orderecommerceRepository.findById(event.getId())
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + event.getId()));

        order.setOrderStatus(OrderStatus.Cancelled);
        orderecommerceRepository.save(order);
        log.info("Order cancelled with ID: {}, reason: {}", event.getId(), event.getReason());
    }
}
