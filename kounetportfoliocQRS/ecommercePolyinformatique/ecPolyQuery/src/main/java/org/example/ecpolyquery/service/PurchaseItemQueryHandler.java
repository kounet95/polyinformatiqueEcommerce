package org.example.ecpolyquery.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.queryhandling.QueryHandler;
import org.example.ecpolyquery.dto.SupplierPageResponse;
import org.example.ecpolyquery.entity.PurchaseItem;
import org.example.ecpolyquery.entity.Supplier;
import org.example.ecpolyquery.query.GetAllPurchaseItemsQuery;
import org.example.ecpolyquery.query.GetAllSuppliersQuery;
import org.example.ecpolyquery.query.GetPurchaseItemByIdQuery;
import org.example.ecpolyquery.query.GetSupplierByIdQuery;
import org.example.ecpolyquery.repos.PurchaseItemRepository;
import org.example.ecpolyquery.repos.SupplierRepository;
import org.example.polyinformatiquecoreapi.dtoEcommerce.SupplierDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class PurchaseItemQueryHandler {

  private final SupplierRepository supplierRepository;

  @QueryHandler
  public SupplierPageResponse handle(GetAllSuppliersQuery query) {
    log.debug("Handling GetAllSuppliersQuery: page={}, size={}", query.getPage(), query.getSize());
    PageRequest pageRequest = PageRequest.of(query.getPage(), query.getSize());
    Page<Supplier> page = supplierRepository.findAll(pageRequest);

    List<SupplierDTO> content = page.getContent()
      .stream()
      .map(supplier -> new SupplierDTO(
        supplier.getId(),
        supplier.getFullname(),
        supplier.getEmail(),
        supplier.getPersonToContact(),
        supplier.getCity()
      ))
      .collect(Collectors.toList());

    return new SupplierPageResponse(
      content,
      page.getNumber(),
      page.getSize(),
      page.getTotalPages(),
      page.getTotalElements()
    );
  }

  @QueryHandler
  public Supplier handle(GetSupplierByIdQuery query) {
    log.debug("Handling GetSupplierByIdQuery: {}", query.getId());
    Optional<Supplier> optionalSupplier = supplierRepository.findById(query.getId());
    return optionalSupplier
      .orElseThrow(() -> new RuntimeException("Supplier not found with id: " + query.getId()));
  }
}
