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

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class ProductSizesQueryHandler {

  private final ProductSizeRepository productSizeRepository;

  @QueryHandler
  public Page<ProductSize> handle(GetAllProductSizesQuery query) {
    log.debug("Handling GetAllProductSizesQuery with pagination: page={}, size={}", query.getPage(), query.getSize());

    Specification<ProductSize> spec = Specification
      .where(ProductSpecification.hasPromoPriceBetween(
        (double) query.getSelectedPrice(),
        (double) query.getSelectedPricePromo()
      ))
      .and(ProductSpecification.hasSize(
        query.getProdsize() != null ? query.getProdsize().name() : null
      ));

    Pageable pageable = PageRequest.of(query.getPage(), query.getSize(), query.getSortOptionAsSortSize());
    return productSizeRepository.findAll(spec, pageable);
  }

  @QueryHandler
  public ProductSize on(GetAllProductSizesByQuery query) {
    log.debug("Handling GetProductByIdQuery: {}", query.getId());
    return productSizeRepository.findById(query.getId())
      .orElseThrow(() -> new RuntimeException("ProductSize not found with id: " + query.getId()));
  }

  @QueryHandler
  public List<ProductSize> search(
    String productName,
    Double minPromo, Double maxPromo,
    String size,
    Boolean sale,
    LocalDateTime newSince,
    String subcategoryId,
    String socialGroupId
  ) {
    Specification<ProductSize> spec = Specification
      .where(ProductSpecification.hasProductsizeName(productName))
      .and(ProductSpecification.hasSubcategoryId(subcategoryId))
      .and(ProductSpecification.hasSocialGroupId(socialGroupId))
      .and(ProductSpecification.hasPromoPriceBetween(minPromo, maxPromo))
      .and(ProductSpecification.hasSize(size))
      .and(sale != null && sale ? ProductSpecification.isOnSale() : null)
      .and(ProductSpecification.isNewArrival(newSince));

    return productSizeRepository.findAll(spec);
  }

  @QueryHandler
  public List<ProductSize> on(FindAllNewsProducts query) {
    log.debug("Handling FindAllNewsProducts Query");
    Date fromDate = query.getDate();
    if (fromDate == null) {
      Calendar cal = Calendar.getInstance();
      cal.add(Calendar.DAY_OF_YEAR, -30);
      fromDate = cal.getTime();
    }
    // Conversion Date -> LocalDateTime
    LocalDateTime since = LocalDateTime.ofInstant(fromDate.toInstant(), ZoneId.systemDefault());
    Specification<ProductSize> spec = ProductSpecification.isNewArrival(since);
    List<ProductSize> newsProductSizes = productSizeRepository.findAll(spec);
    if (newsProductSizes.isEmpty()) {
      throw new RuntimeException("No ProductSizes found for news.");
    }
    return newsProductSizes;
  }

  public List<ProductSize> search(
    String productName,
    Double minPromo, Double maxPromo,
    String size,
    Boolean sale,
    LocalDateTime newSince
  ) {
    Specification<ProductSize> spec = Specification
      .where(ProductSpecification.hasProductsizeName(productName))
      .and(ProductSpecification.hasPromoPriceBetween(minPromo, maxPromo))
      .and(ProductSpecification.hasSize(size))
      .and(sale != null && sale ? ProductSpecification.isOnSale() : null)
      .and(ProductSpecification.isNewArrival(newSince));

    return productSizeRepository.findAll(spec);
  }
  public class FindAllNewsProducts {
    private final Date date;

    public FindAllNewsProducts(Date date) {
      this.date = date;
    }

    public Date getDate() {
      return date;
    }
  }
  public class FindAllSaleProducts {

  }


}
