package org.example.ecpolyquery.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.queryhandling.QueryHandler;
import org.example.ecpolyquery.dto.PageResponse;
import org.example.ecpolyquery.dto.SupplierPageResponse;
import org.example.ecpolyquery.entity.Supplier;
import org.example.ecpolyquery.query.GetAllSuppliersQuery;
import org.example.ecpolyquery.query.GetSupplierByIdQuery;
import org.example.ecpolyquery.repos.SupplierRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class SupplierQueryHandler {

    private final SupplierRepository supplierRepository;

  @QueryHandler
  public Page<Supplier> handle(GetAllSuppliersQuery query) {
    log.debug("Handling GetAllSuppliersQuery with page={}, size={}",
      query.getPage(), query.getSize());

    Pageable pageable = PageRequest.of(query.getPage(), query.getSize());
    return supplierRepository.findAll(pageable);
  }

  @QueryHandler
    public Supplier on(GetSupplierByIdQuery query) {
        log.debug("Handling GetSupplierByIdQuery: {}", query.getId());
        Optional<Supplier> optionalSupplier = supplierRepository.findById(query.getId());
        return optionalSupplier
                .orElseThrow(() -> new RuntimeException("Supplier not found with id: " + query.getId()));
    }
}
