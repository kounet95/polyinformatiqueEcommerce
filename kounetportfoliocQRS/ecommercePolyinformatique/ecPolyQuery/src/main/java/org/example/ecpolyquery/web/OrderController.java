package org.example.ecpolyquery.web;

import lombok.AllArgsConstructor;
import org.axonframework.messaging.responsetypes.ResponseTypes;

import org.axonframework.queryhandling.QueryGateway;
import org.example.ecpolyquery.entity.Orderecommerce;
import org.example.ecpolyquery.query.GetAllOrdersQuery;
import org.example.ecpolyquery.query.GetOrderByIdQuery;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/orders")
@AllArgsConstructor
public class OrderController {

    private final QueryGateway queryGateway;

    @GetMapping
    public CompletableFuture<List<Orderecommerce>> getAllOrders() {
        return queryGateway.query(new GetAllOrdersQuery(), 
                ResponseTypes.multipleInstancesOf(Orderecommerce.class));
    }

    @GetMapping("/{id}")
    public CompletableFuture<Orderecommerce> getOrderById(@PathVariable String id) {
        return queryGateway.query(new GetOrderByIdQuery(id), 
                ResponseTypes.instanceOf(Orderecommerce.class));
    }
}