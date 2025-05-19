package org.example.ecpolyquery.web;

import lombok.AllArgsConstructor;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.example.ecpolyquery.entity.PurchaseItem;
import org.example.ecpolyquery.query.GetAllPurchaseItemsQuery;
import org.example.ecpolyquery.query.GetPurchaseItemByIdQuery;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/purchaseitems")
@AllArgsConstructor
public class PurchaseItemController {

    private final QueryGateway queryGateway;

    @GetMapping
    public CompletableFuture<List<PurchaseItem>> getAllPurchaseItems() {
        return queryGateway.query(new GetAllPurchaseItemsQuery(), 
                ResponseTypes.multipleInstancesOf(PurchaseItem.class));
    }

    @GetMapping("/{id}")
    public CompletableFuture<PurchaseItem> getPurchaseItemById(@PathVariable String id) {
        return queryGateway.query(new GetPurchaseItemByIdQuery(id), 
                ResponseTypes.instanceOf(PurchaseItem.class));
    }
}