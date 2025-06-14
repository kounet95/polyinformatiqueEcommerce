package org.example.ecpolyquery.web;

import lombok.AllArgsConstructor;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.example.ecpolyquery.entity.Product;
import org.example.ecpolyquery.query.GetAllProductsQuery;
import org.example.ecpolyquery.query.GetProductByIdQuery;
import org.example.polyinformatiquecoreapi.dtoEcommerce.ProductDTO;
import org.example.polyinformatiquecoreapi.dtoEcommerce.ProductSizeDTO;
import org.springframework.web.bind.annotation.*;

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
    @RequestParam(required = false) String productSize) {
    return queryGateway.query(
        new GetAllProductsQuery(page, size, categoryId, couleurs, socialGroupId, productSize),
        ResponseTypes.multipleInstancesOf(Product.class)
      )
      .thenApply(products -> products.stream().map(ProductController::toDto).collect(Collectors.toList()));
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
    dto.setPrice(entity.getPrice() != null ? entity.getPrice() : 0.0); // gestion du null
    if (entity.getSizes() != null) {
      dto.setProductSize(ProductSizeDTO.valueOf(entity.getSizes().name()));
    } else {
      dto.setProductSize(null);
    }
    dto.setCreatedAt(entity.getCreatedAt());
    dto.setSubcategoryId(entity.getSubcategory() != null ? entity.getSubcategory().getId() : null);
    dto.setSocialGroupId(entity.getSocialGroup() != null ? entity.getSocialGroup().getId() : null);
    dto.setImageUrl(entity.getUrlimage());
    dto.setIsActive(entity.isActive());
    return dto;
  }
}
