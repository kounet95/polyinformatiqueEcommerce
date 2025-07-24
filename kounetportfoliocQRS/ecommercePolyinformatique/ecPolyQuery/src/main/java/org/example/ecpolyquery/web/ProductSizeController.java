package org.example.ecpolyquery.web;

import lombok.AllArgsConstructor;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.example.ecpolyquery.entity.Product;
import org.example.ecpolyquery.entity.ProductSize;

import org.example.ecpolyquery.query.*;

import org.example.ecpolyquery.repos.ProductSizeRepository;
import org.example.ecpolyquery.service.ProductSizesQueryHandler;
import org.example.polyinformatiquecoreapi.dtoEcommerce.ProductDTO;
import org.example.polyinformatiquecoreapi.dtoEcommerce.ProductSizeDTO;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/productsizes")
@AllArgsConstructor
public class ProductSizeController {

  private final QueryGateway queryGateway;
  private final ProductSizeRepository productSizeRepository;
  private final ProductSizesQueryHandler  productSizesQueryHandler;
  @GetMapping
  @PreAuthorize("hasAuthority('USER')")
  public CompletableFuture<List<ProductSizeDTO>>getAllProductSizes(
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size,
    @RequestParam(required = false) String productSize,
    @RequestParam(defaultValue = "0") int selectedPrice,
    @RequestParam(defaultValue = "0") int selectedPricePromo,
    @RequestParam(required = false) String sortOption
  ) {
    GetAllProductSizesQuery query = new GetAllProductSizesQuery(
      page, size, selectedPrice, selectedPricePromo,
      productSize != null ? org.example.polyinformatiquecoreapi.dtoEcommerce.SizeProd.valueOf(productSize) : null,
      sortOption
    );

    return queryGateway.query(
      query,
      ResponseTypes.multipleInstancesOf(ProductSize.class)
    ).thenApply(productSizes ->
      productSizes.stream().map(ProductSizeController::toDto).collect(Collectors.toList()));

  }
  //un dto pour simplifier
  private static ProductSizeDTO toDto(ProductSize ps) {
    ProductDTO productDTO = null;
    if (ps.getProductId() != null) {
      Product product = ps.getProductId();
      productDTO = ProductDTO.builder()
        .id(product.getId())
        .name(product.getName())
        .description(product.getDescription())
        .isActive(product.isActive())
        .models(product.getUrlModels())
        .subcategoryId(product.getSubcategory() != null ? product.getSubcategory().getId() : null)
        .socialGroupId(product.getSocialGroup() != null ? product.getSocialGroup().getId() : null)
        .build();
    }

    return ProductSizeDTO.builder()
      .id(ps.getId())
      .sizeProd(ps.getSize())
      .prodId(ps.getProductId() != null ? ps.getProductId().getId() : null)
      .price(ps.getPrice())
      .pricePromo(ps.getPromoPrice())
      .frontUrl(ps.getFrontImage())
      .backUrl(ps.getBackImage())
      .leftUrl(ps.getLeftImage())
      .rightUrl(ps.getRightmage())
      .prodId(ps.getProductId() != null ? ps.getProductId().getId() : null)
      .build();
  }


  @GetMapping("/all")
  public CompletableFuture<List<ProductSizeDTO>> getAllProductsise(
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size,


    @RequestParam(required = false) String sortOption
  ) {
    GetAllProductsSizeQuery query = new GetAllProductsSizeQuery(
      page, size
    );

    return queryGateway.query(query, ResponseTypes.multipleInstancesOf(ProductSize.class))
      .thenApply(products -> products.stream()
        .map(ProductSizeController::toDto)
        .collect(Collectors.toList())
      );
  }

  @GetMapping("/search")
  public List<ProductSize> search(
    @RequestParam(required = false) String productName,
    @RequestParam(required = false) Double minPromo,
    @RequestParam(required = false) Double maxPromo,
    @RequestParam(required = false) String size,
    @RequestParam(required = false) Boolean sale,
    @RequestParam(required = false) List<String> couleurs,
    @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime newSince,
    @RequestParam(required = false) String subcategoryId,
    @RequestParam(required = false) String socialGroupId
  ) {
    SearchProductSizesQuery query = new SearchProductSizesQuery(
      productName, minPromo, maxPromo, size, sale, newSince, couleurs, subcategoryId, socialGroupId
    );
    return productSizesQueryHandler.search(query);
  }
  @GetMapping("/{id}")
  public CompletableFuture<ProductSizeDTO> getProductSizeById(@PathVariable String id) {
    GetProductSizeByIdQuery query = new GetProductSizeByIdQuery(id);
    return queryGateway.query(query, ResponseTypes.instanceOf(ProductSize.class))
      .thenApply(ProductSizeController::toDto);
  }





}
