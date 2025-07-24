package org.example.ecpolyquery.web;

import lombok.AllArgsConstructor;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.example.ecpolyquery.entity.OrderLine;
import org.example.ecpolyquery.query.GetAllOrderLinesQuery;
import org.example.ecpolyquery.query.GetOrderLineByIdQuery;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/orderlines")
@AllArgsConstructor
public class OrderLineController {

    private final QueryGateway queryGateway;

    @GetMapping
    public CompletableFuture<List<OrderLine>> getAllOrderLines() {
        return queryGateway.query(new GetAllOrderLinesQuery(), 
                ResponseTypes.multipleInstancesOf(OrderLine.class));
    }

    @GetMapping("/{id}")
    public CompletableFuture<OrderLine> getOrderLineById(@PathVariable String id) {
        return queryGateway.query(new GetOrderLineByIdQuery(id), 
                ResponseTypes.instanceOf(OrderLine.class));
    }
}