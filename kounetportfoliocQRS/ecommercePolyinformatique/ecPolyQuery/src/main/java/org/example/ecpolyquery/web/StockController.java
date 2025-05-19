package org.example.ecpolyquery.web;

import lombok.AllArgsConstructor;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.example.ecpolyquery.entity.Stock;
import org.example.ecpolyquery.query.GetAllStocksQuery;
import org.example.ecpolyquery.query.GetStockByIdQuery;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/stocks")
@AllArgsConstructor
public class StockController {

    private final QueryGateway queryGateway;

    @GetMapping
    public CompletableFuture<List<Stock>> getAllStocks() {
        return queryGateway.query(new GetAllStocksQuery(), 
                ResponseTypes.multipleInstancesOf(Stock.class));
    }

    @GetMapping("/{id}")
    public CompletableFuture<Stock> getStockById(@PathVariable String id) {
        return queryGateway.query(new GetStockByIdQuery(id), 
                ResponseTypes.instanceOf(Stock.class));
    }
}