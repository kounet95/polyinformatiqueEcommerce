package org.example.ecpolyquery.web;

import lombok.AllArgsConstructor;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.example.ecpolyquery.entity.Customer;
import org.example.ecpolyquery.query.GetAllCustomersQuery;
import org.example.ecpolyquery.query.GetCustomerByIdQuery;

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

    @GetMapping("/{id}")
    public CompletableFuture<Customer> getCustomerById(@PathVariable String id) {
        return queryGateway.query(new GetCustomerByIdQuery(id),
                ResponseTypes.instanceOf(Customer.class));
    }
}
