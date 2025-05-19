package org.example.ecpolyquery.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.queryhandling.QueryHandler;
import org.example.ecpolyquery.entity.ProductSize;
import org.example.ecpolyquery.query.GetAllProductSizesQuery;
import org.example.ecpolyquery.query.GetProductSizeByIdQuery;
import org.example.ecpolyquery.repos.ProductSizeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class ProductSizeQueryHandler {

    private final ProductSizeRepository productSizeRepository;

    @QueryHandler
    public List<ProductSize> on(GetAllProductSizesQuery query) {
        log.debug("Handling GetAllProductSizesQuery");
        return productSizeRepository.findAll();
    }

    @QueryHandler
    public ProductSize on(GetProductSizeByIdQuery query) {
        log.debug("Handling GetProductSizeByIdQuery: {}", query.getId());
        Optional<ProductSize> optionalProductSize = productSizeRepository.findById(query.getId());
        return optionalProductSize
                .orElseThrow(() -> new RuntimeException("ProductSize not found with id: " + query.getId()));
    }
}