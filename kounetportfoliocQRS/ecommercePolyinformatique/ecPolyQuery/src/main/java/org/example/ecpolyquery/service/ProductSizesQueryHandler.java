package org.example.ecpolyquery.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.queryhandling.QueryHandler;
import org.example.ecpolyquery.entity.ProductSize;
import org.example.ecpolyquery.query.GetAllProductSizesByQuery;
import org.example.ecpolyquery.query.GetAllProductSizesQuery;
import org.example.ecpolyquery.query.findAllNewsProducts;
import org.example.ecpolyquery.query.findAllSaleProducts;
import org.example.ecpolyquery.repos.ProductSizeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class ProductSizesQueryHandler {

  private final ProductSizeRepository productSizeRepository;

  @QueryHandler
  public List<ProductSize> on(GetAllProductSizesQuery getAllProductSizesQuery){
    log.debug("Handling GetAllProductSizesQuery with pagination: page={}, size={}",
      getAllProductSizesQuery.getPage(), getAllProductSizesQuery.getSize());
    Page<ProductSize> productPage =
      productSizeRepository.findAll(PageRequest.of(
        getAllProductSizesQuery.getPage(),
        getAllProductSizesQuery.getSize()));
    return productPage.getContent();
  }

  @QueryHandler
  public ProductSize on(GetAllProductSizesByQuery query) {
    log.debug("Handling GetProductByIdQuery: {}", query.getId());
    Optional<ProductSize> optionalProduct = productSizeRepository.findById(query.getId());
    return optionalProduct
      .orElseThrow(() -> new RuntimeException("ProductSize not found with id: " + query.getId()));
  }


  @QueryHandler
  public List<ProductSize> on(findAllSaleProducts query) {
    log.debug("Handling FindAllSaleProducts Query");
    List<ProductSize> saleProductSizes = productSizeRepository.findAllSaleProducts();
    if (saleProductSizes == null || saleProductSizes.isEmpty()) {
      throw new RuntimeException("No ProductSizes found on sale.");
    }
    return saleProductSizes;
  }

  @QueryHandler
  public List<ProductSize> on(findAllNewsProducts query) {
    log.debug("Handling FindAllNewsProducts Query");
    List<ProductSize> newsProductSizes = productSizeRepository.findAllNewsProducts(query.getDate());
    if (newsProductSizes == null || newsProductSizes.isEmpty()) {
      throw new RuntimeException("No ProductSizes found for news.");
    }
    return newsProductSizes;
  }

}
