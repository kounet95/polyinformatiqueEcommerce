package org.example.ecpolyquery.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.queryhandling.QueryHandler;
import org.example.ecpolyquery.dto.PageResponse;
import org.example.ecpolyquery.entity.Supplier;
import org.example.ecpolyquery.query.GetAllSuppliersQuery;
import org.example.ecpolyquery.query.GetSupplierByIdQuery;
import org.example.ecpolyquery.repos.SupplierRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class SupplierQueryHandler {

    private final SupplierRepository supplierRepository;

    @QueryHandler
    public PageResponse<Supplier> on(GetAllSuppliersQuery query) {
        log.debug("Handling GetAllSuppliersQuery with pagination: page={}, size={}", query.getPage(), query.getSize());
        Page<Supplier> supplierPage = supplierRepository.findAll(PageRequest.of(query.getPage(), query.getSize()));
        return PageResponse.from(supplierPage);
    }

    @QueryHandler
    public Supplier on(GetSupplierByIdQuery query) {
        log.debug("Handling GetSupplierByIdQuery: {}", query.getId());
        Optional<Supplier> optionalSupplier = supplierRepository.findById(query.getId());
        return optionalSupplier
                .orElseThrow(() -> new RuntimeException("Supplier not found with id: " + query.getId()));
    }
}
