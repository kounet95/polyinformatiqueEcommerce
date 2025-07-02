package org.example.ecpolyquery.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.queryhandling.QueryHandler;
import org.example.ecpolyquery.entity.Product;
import org.example.ecpolyquery.entity.ProductSize;
import org.example.ecpolyquery.query.*;

import org.example.ecpolyquery.repos.ProductSizeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    log.debug("Handling GetAllProductSizesQuery with page={}, size={}", query.getPage(), query.getSize());

    Specification<ProductSize> spec = Specification
      .where(ProductSpecification.hasPromoPriceBetween(query.getSelectedPrice(), query.getSelectedPricePromo()))
      .and(ProductSpecification.hasSize(query.getProdsize() != null ? query.getProdsize().name() : null));

    Pageable pageable = PageRequest.of(query.getPage(), query.getSize(), query.getSortOptionAsSortSize());
    return productSizeRepository.findAll(spec, pageable);
  }
  @QueryHandler
  public Page<ProductSize> handle(GetAllProductsSizeQuery query) {
    log.debug("Handling GetAllProductsQuery with pagination: page={}, size={}",
      query.getPage(), query.getSize());


    Pageable pageable = PageRequest.of(query.getPage(),
      query.getSize());
    return productSizeRepository.findAll( pageable);
  }
  @QueryHandler
  public ProductSize on(GetAllProductSizesByQuery query) {
    log.debug("Handling GetAllProductSizesByQuery: {}", query.getId());
    return productSizeRepository.findById(query.getId())
      .orElseThrow(() -> new RuntimeException("ProductSize not found: " + query.getId()));
  }

  @QueryHandler
  public List<ProductSize> search(SearchProductSizesQuery query) {
    Specification<ProductSize> spec = Specification
      .where(ProductSpecification.hasProductsizeName(query.getProductName()))
      .and(ProductSpecification.hasSubcategoryId(query.getSubcategoryId()))
      .and(ProductSpecification.hasSocialGroupId(query.getSocialGroupId()))
      .and(ProductSpecification.hasPromoPriceBetween(query.getMinPromo(), query.getMaxPromo()))
      .and(ProductSpecification.hasSize(query.getSize()))
      .and(Boolean.TRUE.equals(query.getSale()) ? ProductSpecification.isOnSale() : null)
      .and(ProductSpecification.isNewArrival(query.getNewSince()));

    return productSizeRepository.findAll(spec);
  }

  @QueryHandler
  public List<ProductSize> on(findAllNewsProducts query) {
    log.debug("Handling FindAllNewsProducts query");
    Date fromDate = query.getDate();
    if (fromDate == null) {
      Calendar cal = Calendar.getInstance();
      cal.add(Calendar.DAY_OF_YEAR, -30);
      fromDate = cal.getTime();
    }
    LocalDateTime since = LocalDateTime.ofInstant(fromDate.toInstant(), java.time.ZoneId.systemDefault());
    Specification<ProductSize> spec = ProductSpecification.isNewArrival(since);
    return productSizeRepository.findAll(spec);
  }

  @QueryHandler
  public ProductSize handle(GetProductSizeByIdQuery query) {
    return productSizeRepository.findById(query.getId())
      .orElseThrow(() -> new RuntimeException("ProductSize not found for id: " + query.getId()));
  }

}
