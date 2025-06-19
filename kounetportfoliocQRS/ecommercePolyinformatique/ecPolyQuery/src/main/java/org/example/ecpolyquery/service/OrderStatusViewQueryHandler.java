package org.example.ecpolyquery.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.axonframework.queryhandling.QueryUpdateEmitter;
import org.example.ecpolyquery.entity.OrderStatusView;
import org.example.ecpolyquery.repos.OrderStatusViewRepository;
import org.example.polyinformatiquecoreapi.dtoEcommerce.OrderStatusDTO;
import org.example.polyinformatiquecoreapi.eventEcommerce.OrderScannedEvent;
import org.example.polyinformatiquecoreapi.eventEcommerce.OrderStatusUpdatedEvent;
import org.example.polyinformatiquecoreapi.query.GetOrderStatusByBarcodeQuery;
import org.example.polyinformatiquecoreapi.query.GetOrderStatusByOrderIdQuery;
import org.example.polyinformatiquecoreapi.query.WatchOrderStatusQuery;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class OrderStatusViewQueryHandler {

    private final OrderStatusViewRepository orderStatusViewRepository;
    private final QueryUpdateEmitter queryUpdateEmitter;

    @QueryHandler
    public OrderStatusDTO on(GetOrderStatusByBarcodeQuery query) {
        log.debug("Handling GetOrderStatusByBarcodeQuery: {}", query.getBarcode());
        Optional<OrderStatusView> optionalOrderStatus = orderStatusViewRepository.findByBarcode(query.getBarcode());
        OrderStatusView orderStatusView = optionalOrderStatus
                .orElseThrow(() -> new RuntimeException("Order status not found with barcode: " + query.getBarcode()));
        return convertToDTO(orderStatusView);
    }

    @QueryHandler
    public OrderStatusDTO on(GetOrderStatusByOrderIdQuery query) {
        log.debug("Handling GetOrderStatusByOrderIdQuery: {}", query.getOrderId());
        Optional<OrderStatusView> optionalOrderStatus = orderStatusViewRepository.findByOrderId(query.getOrderId());
        OrderStatusView orderStatusView = optionalOrderStatus
                .orElseThrow(() -> new RuntimeException("Order status not found with orderId: " + query.getOrderId()));
        return convertToDTO(orderStatusView);
    }

    @QueryHandler
    public OrderStatusDTO on(WatchOrderStatusQuery query) {
        log.debug("Handling WatchOrderStatusQuery: {}", query.getOrderId());
        Optional<OrderStatusView> optionalOrderStatus = orderStatusViewRepository.findByOrderId(query.getOrderId());
        if (!optionalOrderStatus.isPresent()) {
            // Try to find by barcode if not found by orderId
            optionalOrderStatus = orderStatusViewRepository.findByBarcode(query.getOrderId());
        }
        OrderStatusView orderStatusView = optionalOrderStatus
                .orElseThrow(() -> new RuntimeException("Order status not found with id or barcode: " + query.getOrderId()));
        return convertToDTO(orderStatusView);
    }

    private OrderStatusDTO convertToDTO(OrderStatusView orderStatusView) {
        return OrderStatusDTO.builder()
                .id(orderStatusView.getId())
                .orderId(orderStatusView.getOrderId())
                .barcode(orderStatusView.getBarcode())
                .status(orderStatusView.getStatus())
                .updatedAt(orderStatusView.getUpdatedAt().format(DateTimeFormatter.ISO_DATE_TIME))
                .build();
    }

    @EventHandler
    public void on(OrderStatusUpdatedEvent event) {
        log.debug("Handling OrderStatusUpdatedEvent for subscription queries: {}", event.getId());
        Optional<OrderStatusView> optionalOrderStatus = orderStatusViewRepository.findByOrderId(event.getId());
        if (optionalOrderStatus.isPresent()) {
            OrderStatusView orderStatusView = optionalOrderStatus.get();
            OrderStatusDTO dto = convertToDTO(orderStatusView);

            // Emit update to subscribers watching by orderId
            queryUpdateEmitter.emit(WatchOrderStatusQuery.class,
                    query -> query.getOrderId().equals(event.getId()),
                    dto);

            // Emit update to subscribers watching by barcode
            if (event.getBarcode() != null && !event.getBarcode().isEmpty()) {
                queryUpdateEmitter.emit(WatchOrderStatusQuery.class,
                        query -> query.getOrderId().equals(event.getBarcode()),
                        dto);
            }
        }
    }

    @EventHandler
    public void on(OrderScannedEvent event) {
        log.debug("Handling OrderScannedEvent for subscription queries: {}", event.getId());
        Optional<OrderStatusView> optionalOrderStatus = orderStatusViewRepository.findByOrderId(event.getId());
        if (optionalOrderStatus.isPresent()) {
            OrderStatusView orderStatusView = optionalOrderStatus.get();
            OrderStatusDTO dto = convertToDTO(orderStatusView);

            // Emit update to subscribers watching by orderId
            queryUpdateEmitter.emit(WatchOrderStatusQuery.class,
                    query -> query.getOrderId().equals(event.getId()),
                    dto);

            // Emit update to subscribers watching by barcode
            if (event.getBarcode() != null && !event.getBarcode().isEmpty()) {
                queryUpdateEmitter.emit(WatchOrderStatusQuery.class,
                        query -> query.getOrderId().equals(event.getBarcode()),
                        dto);
            }
        }
    }
}
