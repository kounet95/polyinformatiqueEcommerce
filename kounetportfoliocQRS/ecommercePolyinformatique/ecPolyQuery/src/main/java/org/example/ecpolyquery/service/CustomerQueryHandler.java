package org.example.ecpolyquery.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.queryhandling.QueryHandler;
import org.example.ecpolyquery.entity.Customer;
import org.example.ecpolyquery.query.GetAllCustomersQuery;
import org.example.ecpolyquery.query.GetCustomerByIdQuery;
import org.example.ecpolyquery.repos.CustomerRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class CustomerQueryHandler {

  private final CustomerRepository customerRepository;

  @QueryHandler
  public List<Customer> on(GetAllCustomersQuery query) {
    log.debug("Handling GetAllCustomersQuery");
    return customerRepository.findAll();
  }

  @QueryHandler
  public Customer on(GetCustomerByIdQuery query) {
    log.debug("Handling GetCustomerByIdQuery: {}", query.getId());
    return customerRepository.findById(query.getId())
      .orElse(null);
  }
}
