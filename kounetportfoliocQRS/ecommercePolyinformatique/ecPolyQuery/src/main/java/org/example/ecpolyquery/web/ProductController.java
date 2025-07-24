package org.example.ecpolyquery.web;

import lombok.AllArgsConstructor;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.example.ecpolyquery.entity.Product;

import org.example.ecpolyquery.entity.ProductSize;
import org.example.ecpolyquery.entity.Subcategory;
import org.example.ecpolyquery.query.GetAllProductsQuery;

import org.example.ecpolyquery.query.GetProductByIdQuery;
import org.example.ecpolyquery.query.GetProductsBySousCategoryQuery;
import org.example.ecpolyquery.repos.ProductRepository;
import org.example.ecpolyquery.service.ProductQueryHandler;
import org.example.polyinformatiquecoreapi.dtoEcommerce.ProductDTO;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
@AllArgsConstructor
public class ProductController {

  private final QueryGateway queryGateway;
private final ProductQueryHandler productQueryHandler;
private final ProductRepository productRepository;
  @GetMapping
  public CompletableFuture<List<ProductDTO>> getAllProducts(
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size,


    @RequestParam(required = false) String sortOption
  ) {
    GetAllProductsQuery query = new GetAllProductsQuery(
      page, size
    );

    return queryGateway.query(query, ResponseTypes.multipleInstancesOf(Product.class))
      .thenApply(products -> products.stream()
        .map(ProductController::toDto)
        .collect(Collectors.toList())
      );
  }



  @GetMapping("/{id}")
  public CompletableFuture<ProductDTO> getProductById(@PathVariable String id) {
    return queryGateway.query(
        new GetProductByIdQuery(id),
        ResponseTypes.instanceOf(Product.class)
      )
      .thenApply(ProductController::toDto);
  }

  private static ProductDTO toDto(Product entity) {
    if (entity == null) return null;
    ProductDTO dto = new ProductDTO();
    dto.setId(entity.getId());
    dto.setName(entity.getName());
    dto.setDescription(entity.getDescription());
    dto.setSubcategoryId(entity.getSubcategory() != null ? entity.getSubcategory().getId() : null);
    dto.setSocialGroupId(entity.getSocialGroup() != null ? entity.getSocialGroup().getId() : null);
    dto.setModels(entity.getUrlModels());
    dto.setIsActive(entity.isActive());
    return dto;
  }


  @GetMapping("/by-souscategorie/{id}")
  public CompletableFuture<List<ProductDTO>> getProductsBySousCategoryId(@PathVariable("id") String sousCategoryId) {
    return queryGateway.query(
      new GetProductsBySousCategoryQuery(sousCategoryId),
      ResponseTypes.multipleInstancesOf(Product.class)
    ).thenApply(products -> products.stream()
      .map(ProductController::toDto)
      .collect(Collectors.toList())
    );
  }


}
