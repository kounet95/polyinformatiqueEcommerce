package org.example.ecpolyquery.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.example.ecpolyquery.entity.OrderLine;
import org.example.ecpolyquery.entity.Orderecommerce;
import org.example.ecpolyquery.entity.Stock;
import org.example.ecpolyquery.repos.OrderLineRepository;
import org.example.ecpolyquery.repos.OrderecommerceRepository;
import org.example.ecpolyquery.repos.ProductRepository;
import org.example.ecpolyquery.repos.StockRepository;
import org.example.polyinformatiquecoreapi.dtoEcommerce.OrderLineDTO;
import org.example.polyinformatiquecoreapi.eventEcommerce.OrderLineDeletedEvent;
import org.example.polyinformatiquecoreapi.eventEcommerce.OrderLineUpdatedEvent;
import org.example.polyinformatiquecoreapi.eventEcommerce.ProductAddedToOrderEvent;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class OrderLineService {

    private final OrderLineRepository orderLineRepository;
    private final OrderecommerceRepository orderecommerceRepository;
    private final StockRepository  stockRepository;

    @EventHandler
    public void on(ProductAddedToOrderEvent event) {
        log.debug("Handling ProductAddedToOrderEvent: {}", event.getId());
        OrderLineDTO orderLineDTO = event.getOrderLineDTO();

        Orderecommerce order = orderecommerceRepository.findById(orderLineDTO.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderLineDTO.getOrderId()));
      Stock stock = stockRepository.findById(orderLineDTO.getStockId()).orElseThrow(()
        -> new RuntimeException("Stock not found with id: " + orderLineDTO.getStockId()));

        OrderLine orderLine = OrderLine.builder()
                .id(orderLineDTO.getId())
                .qty(orderLineDTO.getQty())
                .orderecommerce(order)
                .stockId(stock)
                 .build();
        orderLineRepository.save(orderLine);
        log.info("OrderLine created with ID: {}", orderLine.getId());
    }

  @EventHandler
  public void on(OrderLineUpdatedEvent event) {
    log.debug("Handling OrderLineUpdatedEvent: {}", event.getOrderLineDTO().getId());
    OrderLineDTO orderLineDTO = event.getOrderLineDTO();
    OrderLine orderLine = orderLineRepository.findById(orderLineDTO.getId())
      .orElseThrow(() -> new RuntimeException("OrderLine not found with id: " + orderLineDTO.getId()));
    orderLine.setQty(orderLineDTO.getQty());
    orderLineRepository.save(orderLine);
    log.info("OrderLine updated with ID: {}", orderLine.getId());
  }

  @EventHandler
  public void on(OrderLineDeletedEvent event) {
    log.debug("Handling OrderLineDeletedEvent: {}", event.getIdString());
    if (orderLineRepository.existsById(event.getIdString())) {
      orderLineRepository.deleteById(event.getIdString());
      log.info("OrderLine deleted with ID: {}", event.getIdString());
    } else {
      log.warn("Tried to delete non-existent OrderLine with ID: {}", event.getIdString());
    }
  }
}
