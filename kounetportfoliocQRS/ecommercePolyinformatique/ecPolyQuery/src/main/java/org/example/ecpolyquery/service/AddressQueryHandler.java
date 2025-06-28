package org.example.ecpolyquery.service;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.queryhandling.QueryHandler;
import org.example.ecpolyquery.entity.Address;
import org.example.ecpolyquery.entity.Category;
import org.example.ecpolyquery.query.GetAddressByIdQuery;
import org.example.ecpolyquery.query.GetAddressesByCustomerIdQuery;
import org.example.ecpolyquery.query.GetAllAddressQuery;
import org.example.ecpolyquery.query.GetAllCategoriesQuery;
import org.example.ecpolyquery.query.GetCategoryByIdQuery;
import org.example.ecpolyquery.repos.AddressRepository;
import org.example.ecpolyquery.repos.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class AddressQueryHandler {
  private final AddressRepository addressRepository;

  @QueryHandler
  public List<Address> on(GetAllAddressQuery query) {
    log.debug("Handling GetAllCategoriesQuery");
    return addressRepository.findAll();
  }

  @QueryHandler
  public Address on(GetAddressByIdQuery query) {
    log.debug("Handling GetAddressByIdQuery: {}", query.getId());
    Optional<Address> optionalAddress = addressRepository.findById(query.getId());
    return optionalAddress
      .orElseThrow(() -> new RuntimeException("Address not found with id: " + query.getId()));
  }

  @QueryHandler
  public List<Address> on(GetAddressesByCustomerIdQuery query) {
    log.debug("Handling GetAddressesByCustomerIdQuery: {}", query.getCustomerId());
    return addressRepository.findByCustomer_Id(query.getCustomerId());
  }
}
