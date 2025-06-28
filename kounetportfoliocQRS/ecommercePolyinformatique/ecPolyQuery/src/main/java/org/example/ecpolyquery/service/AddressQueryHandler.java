package org.example.ecpolyquery.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.queryhandling.QueryHandler;
import org.example.ecpolyquery.entity.Address;
import org.example.ecpolyquery.entity.AddressLink;
import org.example.ecpolyquery.query.GetAddressByIdQuery;
import org.example.ecpolyquery.query.GetAddressesByCustomerIdQuery;
import org.example.ecpolyquery.query.GetAllAddressQuery;
import org.example.ecpolyquery.repos.AddressRepository;
import org.example.ecpolyquery.repos.AddressLinkRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class AddressQueryHandler {

  private final AddressRepository addressRepository;
  private final AddressLinkRepository addressLinkRepository;

  @QueryHandler
  public List<Address> on(GetAllAddressQuery query) {
    log.debug("Handling GetAllAddressQuery");
    return addressRepository.findAll();
  }

  @QueryHandler
  public Address on(GetAddressByIdQuery query) {
    log.debug("Handling GetAddressByIdQuery: {}", query.getId());
    return addressRepository.findById(query.getId())
      .orElseThrow(() -> new RuntimeException("Address not found with id: " + query.getId()));
  }

  @QueryHandler
  public List<Address> on(GetAddressesByCustomerIdQuery query) {
    log.debug("Handling GetAddressesByCustomerIdQuery: {}", query.getCustomerId());
    List<AddressLink> links = addressLinkRepository.findByTargetTypeAndTargetId("CUSTOMER", query.getCustomerId());
    return links.stream()
      .map(AddressLink::getAddress)
      .toList();
  }
}
