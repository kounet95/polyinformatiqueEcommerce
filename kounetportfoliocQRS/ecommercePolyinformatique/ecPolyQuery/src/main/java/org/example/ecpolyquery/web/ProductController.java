package org.example.ecpolyquery.web;

import lombok.AllArgsConstructor;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.example.ecpolyquery.entity.Product;
import org.example.ecpolyquery.query.GetAllProductsQuery;
import org.example.ecpolyquery.query.GetProductByIdQuery;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/products")
@AllArgsConstructor
public class ProductController {

  private final QueryGateway queryGateway;

  @GetMapping
  public CompletableFuture<List<Product>> getAllProducts(
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size,
    @RequestParam(required = false) String categoryId,
    @RequestParam(required = false) String couleurs,
    @RequestParam(required = false) String socialGroupId,
    @RequestParam(required = false) String productSize) {
    return queryGateway.query(
      new GetAllProductsQuery(page, size, categoryId, couleurs, socialGroupId, productSize),
      ResponseTypes.multipleInstancesOf(Product.class)
    );
  }

  @GetMapping("/{id}")
  public CompletableFuture<Product> getProductById(@PathVariable String id) {
    return queryGateway.query(
      new GetProductByIdQuery(id),
      ResponseTypes.instanceOf(Product.class)
    );
  }
}
