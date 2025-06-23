package org.example.ecpolyquery.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.queryhandling.QueryHandler;
import org.example.ecpolyquery.entity.ProductSize;
import org.example.ecpolyquery.query.*;
import org.example.ecpolyquery.repos.ProductSizeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class ProductSizesQueryHandler {

  private final ProductSizeRepository productSizeRepository;

  @QueryHandler
  public Page<ProductSize> handle(GetAllProductSizesQuery query) {
    log.debug("Handling GetAllProductSizesQuery with pagination: page={}, size={}",
      query.getPage(), query.getSize());

    Specification<ProductSize> spec = ProductSpecification.withFiltersSize(
      query.getSelectedPrice(),
      query.getSelectedPricePromo(),
      query.getProdsize()
    );
    Pageable pageable = PageRequest.of(query.getPage(), query.getSize(), query.getSortOptionAsSortSize());
    return productSizeRepository.findAll(spec, pageable);
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
    // Calculons la date il y a 30 jours si le query n’amene pas déjà une Date
    Date fromDate = query.getDate();
    if (fromDate == null) {
      Calendar cal = Calendar.getInstance();
      cal.add(Calendar.DAY_OF_YEAR, -30);
      fromDate = cal.getTime();
    }
    List<ProductSize> newsProductSizes = productSizeRepository.findAllNewsProducts(fromDate);
    if (newsProductSizes == null || newsProductSizes.isEmpty()) {
      throw new RuntimeException("No ProductSizes found for news.");
    }
    return newsProductSizes;
  }
}
