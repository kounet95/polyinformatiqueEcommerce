package org.example.ecpolyquery.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.queryhandling.QueryHandler;
import org.example.ecpolyquery.entity.Product;
import org.example.ecpolyquery.entity.ProductSize;
import org.example.ecpolyquery.query.GetAllProductsQuery;
import org.example.ecpolyquery.query.GetProductByIdQuery;
import org.example.ecpolyquery.repos.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.jpa.domain.Specification.where;

@Service
@Slf4j
@AllArgsConstructor
public class ProductQueryHandler {

  private final ProductRepository productRepository;

  @QueryHandler
  public Page<Product> handle(GetAllProductsQuery query) {
    log.debug("Handling GetAllProductsQuery with pagination: page={}, size={}",
      query.getPage(), query.getSize());


    Pageable pageable = PageRequest.of(query.getPage(), query.getSize(), query.getSortOptionAsSort());
    return productRepository.findAll( pageable);
  }

  @QueryHandler
  public Product handle(GetProductByIdQuery query) {
    log.debug("Handling GetProductByIdQuery: {}", query.getId());
    Optional<Product> optionalProduct = productRepository.findById(query.getId());
    return optionalProduct.orElseThrow(() -> new RuntimeException("Product not found with id: " + query.getId()));
  }

}
