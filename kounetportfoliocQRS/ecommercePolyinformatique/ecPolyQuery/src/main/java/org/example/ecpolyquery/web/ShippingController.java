package org.example.ecpolyquery.web;

import lombok.AllArgsConstructor;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.example.ecpolyquery.entity.Shipping;
import org.example.ecpolyquery.query.GetAllShippingsQuery;
import org.example.ecpolyquery.query.GetShippingByIdQuery;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/shippings")
@AllArgsConstructor
public class ShippingController {

    private final QueryGateway queryGateway;

    @GetMapping
    public CompletableFuture<List<Shipping>> getAllShippings() {
        return queryGateway.query(new GetAllShippingsQuery(), 
                ResponseTypes.multipleInstancesOf(Shipping.class));
    }

    @GetMapping("/{id}")
    public CompletableFuture<Shipping> getShippingById(@PathVariable String id) {
        return queryGateway.query(new GetShippingByIdQuery(id), 
                ResponseTypes.instanceOf(Shipping.class));
    }
}