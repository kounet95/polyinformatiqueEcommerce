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
import org.example.ecpolyquery.service.ProductSizesQueryHandler;
import org.example.polyinformatiquecoreapi.dtoEcommerce.ProductDTO;
import org.example.polyinformatiquecoreapi.dtoEcommerce.ProductSizeDTO;
import org.example.polyinformatiquecoreapi.dtoEcommerce.SizeProd;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
  private final ProductSizesQueryHandler  productSizesQueryHandler;
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

  @GetMapping(params = "productId")
  public List<ProductSize> getProductSizesByProductId(@RequestParam String productId) {
    return productSizeRepository.findByProductId_Id(productId);
  }

  @GetMapping("/search")
  public List<ProductSize> search(
    @RequestParam(required = false) String productName,
    @RequestParam(required = false) Double minPromo,
    @RequestParam(required = false) Double maxPromo,
    @RequestParam(required = false) String size,
    @RequestParam(required = false) Boolean sale,
    @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime newSince,
    @RequestParam(required = false) String subcategoryId,
    @RequestParam(required = false) String socialGroupId
  ) {
    return productSizesQueryHandler.search(
      productName, minPromo,
      maxPromo, size, sale, newSince,
      subcategoryId, socialGroupId);
  }
}
