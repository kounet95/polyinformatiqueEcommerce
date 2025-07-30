package org.example.ecpolyquery.web;

import lombok.AllArgsConstructor;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.example.ecpolyquery.entity.Customer;
import org.example.ecpolyquery.query.GetAllCustomersQuery;
import org.example.ecpolyquery.query.GetCustomerByEmailQuery;
import org.example.ecpolyquery.query.GetCustomerByIdQuery;

import org.example.ecpolyquery.repos.CustomerRepository;
import org.example.polyinformatiquecoreapi.dtoEcommerce.CustomerEcommerceDTO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/customers")
@AllArgsConstructor
public class CustomerController {

    private final QueryGateway queryGateway;
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public CompletableFuture<List<Customer>> getAllCustomers() {
        return queryGateway.query(new GetAllCustomersQuery(),
                ResponseTypes.multipleInstancesOf(Customer.class));
    }

  @GetMapping("/by-email/{email}")
  public CustomerEcommerceDTO getCustomerByEmail(@PathVariable String email) {
    return queryGateway.query(
      new GetCustomerByEmailQuery(email),
      ResponseTypes.instanceOf(CustomerEcommerceDTO.class)
    ).join();
  }



  @GetMapping("/{id}")
  public CompletableFuture<CustomerEcommerceDTO> getCustomerById(@PathVariable String id) {
    return queryGateway.query(
      new GetCustomerByIdQuery(id),
      ResponseTypes.instanceOf(CustomerEcommerceDTO.class)
    );
  }
}
