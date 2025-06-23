package org.example.ecpolyquery.web;

import lombok.AllArgsConstructor;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.example.ecpolyquery.entity.Product;
import org.example.ecpolyquery.entity.ProductSize;

import org.example.ecpolyquery.query.GetAllProductSizesQuery;

import org.example.ecpolyquery.query.GetProductSizeByIdQuery;
import org.example.ecpolyquery.query.findAllNewsProducts;
import org.example.ecpolyquery.query.findAllSaleProducts;
import org.example.ecpolyquery.repos.ProductSizeRepository;
import org.example.polyinformatiquecoreapi.dtoEcommerce.ProductDTO;
import org.example.polyinformatiquecoreapi.dtoEcommerce.ProductSizeDTO;
import org.example.polyinformatiquecoreapi.dtoEcommerce.SizeProd;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/productsizes")
@AllArgsConstructor
public class ProductSizeController {

  private final QueryGateway queryGateway;
private final ProductSizeRepository productSizeRepository;
  @GetMapping
  public CompletableFuture<List<ProductSize>> getAllProductSizes(
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size,
    @RequestParam(required = false) SizeProd productSize,
    @RequestParam(defaultValue = "0") int selectedPrice,
    @RequestParam(defaultValue = "0") int selectedPricePromo,
    @RequestParam(required = false) String sortOption
  ) {
    GetAllProductSizesQuery query = new GetAllProductSizesQuery(
      page,
      size,
      selectedPrice,
      selectedPricePromo,
      productSize,
      sortOption
    );

    return queryGateway.query(query, ResponseTypes.multipleInstancesOf(ProductSize.class));
  }

  @GetMapping("/{id}")
  public CompletableFuture<ProductSize> getProductSizeById(@PathVariable String id) {
    return queryGateway.query(new GetProductSizeByIdQuery(id),
      ResponseTypes.instanceOf(ProductSize.class));
  }

  @GetMapping("news/{date}")

  public CompletableFuture<List<ProductSizeDTO>> getAllNewsProducts( @PathVariable
                                                                       @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                                       Date date) {
    return queryGateway.query(
      new findAllNewsProducts(date),
      ResponseTypes.multipleInstancesOf(ProductSize.class)
    ).thenApply(ProductSizeController::toDtoList);
  }

  @GetMapping("sale")
  public CompletableFuture<List<ProductSizeDTO>> getAllSaleProducts() {
    return queryGateway.query(
      new findAllSaleProducts(),
      ResponseTypes.multipleInstancesOf(ProductSize.class)
    ).thenApply(ProductSizeController::toDtoList);
  }


  public static List<ProductSizeDTO> toDtoList(List<ProductSize> productSizes) {
    return productSizes.stream()
      .map(ProductSizeController::toDto)
      .collect(Collectors.toList());
  }

  public static ProductSizeDTO toDto(ProductSize productSize) {
    ProductSizeDTO dto = new ProductSizeDTO();
    dto.setId(productSize.getId());
    dto.setSizeProd(productSize.getSize());
    dto.setImageUrl(productSize.getUrlImage());
    dto.setPrice(productSize.getPrice());
    dto.setPricePromo(productSize.getPromoPrice());
    return dto;
  }

}
