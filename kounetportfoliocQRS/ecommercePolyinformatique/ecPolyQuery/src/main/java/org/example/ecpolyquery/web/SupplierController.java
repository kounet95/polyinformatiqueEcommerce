package org.example.ecpolyquery.web;

import lombok.AllArgsConstructor;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.example.ecpolyquery.dto.PageResponse;
import org.example.ecpolyquery.entity.Supplier;
import org.example.ecpolyquery.query.GetAllSuppliersQuery;
import org.example.ecpolyquery.query.GetSupplierByIdQuery;
import org.example.polyinformatiquecoreapi.dtoEcommerce.SupplierDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/suppliers")
@AllArgsConstructor
public class SupplierController {

  private final QueryGateway queryGateway;

  @GetMapping
  public CompletableFuture<List<SupplierDTO>> getAllSuppliers(
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size
  ) {
    GetAllSuppliersQuery query = new GetAllSuppliersQuery(page, size);

    return queryGateway.query(
      query,
      ResponseTypes.multipleInstancesOf(Supplier.class)
    ).thenApply(supplier ->
      supplier.stream()
        .map(SupplierController::toDto)
        .collect(Collectors.toList()));
  }

private static SupplierDTO toDto(Supplier supplier) {
    return SupplierDTO.builder()
      .id(supplier.getId())
      .fullname(supplier.getFullname())
      .email(supplier.getEmail())
      .personToContact(supplier.getPersonToContact())
      .build();
}

  @GetMapping("/{id}")
  public CompletableFuture<Supplier> getSupplierById(@PathVariable String id) {
    return queryGateway.query(
      new GetSupplierByIdQuery(id),
      ResponseTypes.instanceOf(Supplier.class)
    );
  }
}
