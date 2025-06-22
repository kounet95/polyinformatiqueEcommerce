package org.example.ecpolyquery.web;

import lombok.AllArgsConstructor;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.example.ecpolyquery.entity.Product;

import org.example.ecpolyquery.query.GetAllProductsQuery;

import org.example.ecpolyquery.query.GetProductByIdQuery;
import org.example.polyinformatiquecoreapi.dtoEcommerce.ProductDTO;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
@AllArgsConstructor
public class ProductController {

  private final QueryGateway queryGateway;

  @GetMapping
  public CompletableFuture<List<ProductDTO>> getAllProducts(
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size,
    @RequestParam(required = false) String categoryId,
    @RequestParam(required = false) String couleurs,
    @RequestParam(required = false) String socialGroupId,
    @RequestParam(required = false) String productSize,
    @RequestParam(required = false) String sousCategories,
    @RequestParam(required = false) String searchKeyword,
    @RequestParam(required = false) String selectedPriceRange,
    @RequestParam(required = false) String sortOption
  ) {
    GetAllProductsQuery query = new GetAllProductsQuery(
      page, size, categoryId, couleurs, socialGroupId,
      productSize, sousCategories, searchKeyword, selectedPriceRange, sortOption
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
    dto.setCreatedAt(entity.getCreatedAt());
    dto.setSubcategoryId(entity.getSubcategory() != null ? entity.getSubcategory().getId() : null);
    dto.setSocialGroupId(entity.getSocialGroup() != null ? entity.getSocialGroup().getId() : null);
    dto.setModels(entity.getUrlModels());
    dto.setIsActive(entity.isActive());
    return dto;
  }
}
