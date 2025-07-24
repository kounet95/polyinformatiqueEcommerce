package org.example.ecpolyquery.web;


import lombok.AllArgsConstructor;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.example.ecpolyquery.entity.Stock;
import org.example.ecpolyquery.entity.Supplier;
import org.example.ecpolyquery.entity.Supply;
import org.example.ecpolyquery.query.GetAllSuppliersQuery;
import org.example.ecpolyquery.query.GetAllSupplyQuery;
import org.example.ecpolyquery.query.GetSupplierByIdQuery;
import org.example.polyinformatiquecoreapi.dtoEcommerce.SupplierDTO;
import org.example.polyinformatiquecoreapi.dtoEcommerce.SupplyDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/supply")
@AllArgsConstructor
public class SupplyController {
  private final QueryGateway queryGateway;

  @GetMapping
  public CompletableFuture<List<SupplyDTO>> getAllSuppliers(
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size
  ) {
    GetAllSupplyQuery query = new GetAllSupplyQuery(page, size);

    return queryGateway.query(
      query,
      ResponseTypes.multipleInstancesOf(Supply.class)
    ).thenApply(supplier ->
      supplier.stream()
        .map(SupplyController::toDto)
        .collect(Collectors.toList()));
  }

  private static SupplyDTO toDto(Supply supply) {
    return SupplyDTO.builder()
      .id(supply.getId())
      .name(supply.getName())
      .createdAt(supply.getCreated_at())
      .stocksIds(supply.getStockList().stream()
        .map(Stock::getId)
        .collect(Collectors.toList())
      )
      .build();
  }

  @GetMapping("/{id}")
  public CompletableFuture<Supply> getSupplierById(@PathVariable String id) {
    return queryGateway.query(
      new GetSupplierByIdQuery(id),
      ResponseTypes.instanceOf(Supply.class)
    );
  }
}
