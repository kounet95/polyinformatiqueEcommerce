package org.example.ecpolyquery.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.queryhandling.QueryHandler;
import org.example.ecpolyquery.entity.Customer;
import org.example.ecpolyquery.entity.Orderecommerce;
import org.example.ecpolyquery.query.CustomerOrder;
import org.example.ecpolyquery.query.GetAllOrdersQuery;
import org.example.ecpolyquery.query.GetOrderByIdQuery;
import org.example.ecpolyquery.repos.CustomerRepository;
import org.example.ecpolyquery.repos.OrderecommerceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class OrderQueryHandler {

    private final OrderecommerceRepository orderecommerceRepository;
 private final CustomerRepository customerRepository;
    @QueryHandler
    public List<Orderecommerce> on(GetAllOrdersQuery query) {
        log.debug("Handling GetAllOrdersQuery");
        return orderecommerceRepository.findAll();
    }

    @QueryHandler
    public Orderecommerce on(GetOrderByIdQuery query) {
        log.debug("Handling GetOrderByIdQuery: {}", query.getId());
        Optional<Orderecommerce> optionalOrder = orderecommerceRepository.findById(query.getId());
        return optionalOrder
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + query.getId()));
    }

    @QueryHandler
  public List<Orderecommerce> handle(CustomerOrder query) {
      log.debug("Handling GetCustomerOrderByIdQuery" ,query.getCustomerId());
      Customer customer = customerRepository.findById(query.getCustomerId())
        .orElseThrow(() -> new EntityNotFoundException("Customer not found il ma pas fait de commande pour linstant: " + query.getCustomerId()));

      return  orderecommerceRepository.findAll().stream()
        .filter(orderecommerce -> orderecommerce.getCustomer().getId().equals(customer.getId()))
        .toList();

    }
}
