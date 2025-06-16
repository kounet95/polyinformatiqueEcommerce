package org.example.ecpolyquery.web;

import lombok.AllArgsConstructor;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.example.ecpolyquery.query.GetAllPurchasesQuery;
import org.example.ecpolyquery.query.GetPurchaseByIdQuery;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/purchases")
@AllArgsConstructor
public class PurchaseController {

    private final QueryGateway queryGateway;

    @GetMapping
    public CompletableFuture<List<Purchase>> getAllPurchases() {
        return queryGateway.query(new GetAllPurchasesQuery(),
                ResponseTypes.multipleInstancesOf(Purchase.class));
    }

    @GetMapping("/{id}")
    public CompletableFuture<Purchase> getPurchaseById(@PathVariable String id) {
        return queryGateway.query(new GetPurchaseByIdQuery(id),
                ResponseTypes.instanceOf(Purchase.class));
    }
}
