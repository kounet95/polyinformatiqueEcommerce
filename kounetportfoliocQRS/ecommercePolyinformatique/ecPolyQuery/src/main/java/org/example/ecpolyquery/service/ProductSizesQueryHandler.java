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
    List<ProductSize> finalResult;

    Specification<ProductSize> spec = Specification.where(null);

    if (query.getProductName() != null) {
      Specification<ProductSize> s = ProductSpecification.hasProductsizeName(query.getProductName());
      List<ProductSize> byName = productSizeRepository.findAll(s);
      System.out.println("Filtre productName => " + byName.size() + " résultats");
      spec = spec.and(s);
    }

    if (query.getSubcategoryId() != null) {
      Specification<ProductSize> s = ProductSpecification.hasSubcategoryId(query.getSubcategoryId());
      List<ProductSize> bySub = productSizeRepository.findAll(s);
      System.out.println("Filtre subcategoryId => " + bySub.size() + " résultats");
      spec = spec.and(s);
    }

    if (query.getSocialGroupId() != null) {
      Specification<ProductSize> s = ProductSpecification.hasSocialGroupId(query.getSocialGroupId());
      List<ProductSize> bySocial = productSizeRepository.findAll(s);
      System.out.println("Filtre socialGroupId => " + bySocial.size() + " résultats");
      spec = spec.and(s);
    }

    if (query.getMinPromo() != null || query.getMaxPromo() != null) {
      Specification<ProductSize> s = ProductSpecification.hasPromoPriceBetween(query.getMinPromo(), query.getMaxPromo());
      List<ProductSize> byPromo = productSizeRepository.findAll(s);
      System.out.println("Filtre promoPrice => " + byPromo.size() + " résultats");
      spec = spec.and(s);
    }

    if (query.getSize() != null) {
      Specification<ProductSize> s = ProductSpecification.hasSize(query.getSize());
      List<ProductSize> bySize = productSizeRepository.findAll(s);
      System.out.println("Filtre size => " + bySize.size() + " résultats");
      spec = spec.and(s);
    }

    if (Boolean.TRUE.equals(query.getSale())) {
      Specification<ProductSize> s = ProductSpecification.isOnSale();
      List<ProductSize> bySale = productSizeRepository.findAll(s);
      System.out.println("Filtre isOnSale => " + bySale.size() + " résultats");
      spec = spec.and(s);
    }

    if (query.getNewSince() != null) {
      Specification<ProductSize> s = ProductSpecification.isNewArrival(query.getNewSince());
      List<ProductSize> byNew = productSizeRepository.findAll(s);
      System.out.println("Filtre newArrival => " + byNew.size() + " résultats");
      spec = spec.and(s);
    }

    // Résultat final avec TOUS les filtres combinés
    finalResult = productSizeRepository.findAll(spec);

    System.out.println("Résultat FINAL (AND combiné) => " + finalResult.size() + " résultats");

    return finalResult;
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
