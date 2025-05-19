package org.example.ecpolyquery.web;

import lombok.AllArgsConstructor;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.example.ecpolyquery.entity.ProductSize;
import org.example.ecpolyquery.query.GetAllProductSizesQuery;
import org.example.ecpolyquery.query.GetProductSizeByIdQuery;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/productsizes")
@AllArgsConstructor
public class ProductSizeController {

    private final QueryGateway queryGateway;

    @GetMapping
    public CompletableFuture<List<ProductSize>> getAllProductSizes() {
        return queryGateway.query(new GetAllProductSizesQuery(), 
                ResponseTypes.multipleInstancesOf(ProductSize.class));
    }

    @GetMapping("/{id}")
    public CompletableFuture<ProductSize> getProductSizeById(@PathVariable String id) {
        return queryGateway.query(new GetProductSizeByIdQuery(id), 
                ResponseTypes.instanceOf(ProductSize.class));
    }
}