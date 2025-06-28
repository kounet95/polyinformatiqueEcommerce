package org.example.ecpolyquery.web;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.example.ecpolyquery.entity.Address;
import org.example.ecpolyquery.entity.Category;
import org.example.ecpolyquery.query.GetAddressByIdQuery;
import org.example.ecpolyquery.query.GetAddressesByCustomerIdQuery;
import org.example.ecpolyquery.query.GetAllAddressQuery;
import org.example.ecpolyquery.query.GetAllCategoriesQuery;
import org.example.ecpolyquery.query.GetCategoryByIdQuery;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/address")
@AllArgsConstructor
@Slf4j
public class AddressController {

  private final QueryGateway queryGateway;

  @GetMapping
  @PreAuthorize("hasAuthority('USER')")
  public ResponseEntity<?> getAddresses() {

    try {
      List<Address> addresses = queryGateway.query(new GetAllAddressQuery(),
        ResponseTypes.multipleInstancesOf(Address.class)).join();
      return ResponseEntity.ok(addresses);
    } catch (Exception e) {
      log.error("Erreur lors de la récupération des address", e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }

  }


  @GetMapping("/{id}")
  public CompletableFuture<Address> getAddressById(@PathVariable String id) {
    return queryGateway.query(new GetAddressByIdQuery(id),
      ResponseTypes.instanceOf(Address.class));
  }

  @GetMapping("/customer/{customerId}")
  @PreAuthorize("hasAuthority('USER')")
  public ResponseEntity<?> findAllAddressesByCustomerId(@PathVariable String customerId) {
    try {
      List<Address> addresses = queryGateway.query(new GetAddressesByCustomerIdQuery(customerId),
        ResponseTypes.multipleInstancesOf(Address.class)).join();
      return ResponseEntity.ok(addresses);
    } catch (Exception e) {
      log.error("Erreur lors de la récupération des adresses pour le client: " + customerId, e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
  }
}
