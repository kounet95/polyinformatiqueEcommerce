package org.example.ecpolyquery.web;

import lombok.RequiredArgsConstructor;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.SubscriptionQueryResult;
import org.example.polyinformatiquecoreapi.dtoEcommerce.OrderStatusDTO;
import org.example.polyinformatiquecoreapi.query.WatchOrderStatusQuery;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/orderstatus")
@RequiredArgsConstructor
public class OrderStatusController {

    private final QueryGateway queryGateway;

    @GetMapping(value = "/watch/{orderId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<OrderStatusDTO> watchOrderStatus(@PathVariable String orderId) {
        SubscriptionQueryResult<OrderStatusDTO, OrderStatusDTO> result = queryGateway.subscriptionQuery(
                new WatchOrderStatusQuery(orderId),
                ResponseTypes.instanceOf(OrderStatusDTO.class),
                ResponseTypes.instanceOf(OrderStatusDTO.class)
        );
        return result.initialResult().concatWith(result.updates());
    }

    @GetMapping(value = "/watch-by-barcode/{barcode}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<OrderStatusDTO> watchOrderStatusByBarcode(@PathVariable String barcode) {
        SubscriptionQueryResult<OrderStatusDTO, OrderStatusDTO> result = queryGateway.subscriptionQuery(
                new WatchOrderStatusQuery(barcode),
                ResponseTypes.instanceOf(OrderStatusDTO.class),
                ResponseTypes.instanceOf(OrderStatusDTO.class)
        );
        return result.initialResult().concatWith(result.updates());
    }
}
