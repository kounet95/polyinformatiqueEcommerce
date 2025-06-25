package org.example.ecpolyquery.service;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.example.ecpolyquery.entity.*;
import org.example.ecpolyquery.repos.*;
import org.example.polyinformatiquecoreapi.dtoEcommerce.AddressDTO;
import org.example.polyinformatiquecoreapi.dtoEcommerce.CreatedAddressEvent;
import org.example.polyinformatiquecoreapi.eventEcommerce.DeletedAddressEvent;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class AddressService {

  private AddressRepository addressRepository;
  private SupplierRepository  supplierRepository;
  private ShippingRepository shippingRepository;
  private CustomerRepository customerRepository;
  private StockRepository  stockRepository;
  @EventHandler
  public void on(CreatedAddressEvent event) {
    log.debug("Handling CreatedAddressEvent: {}", event.getId());
    AddressDTO dto = event.getAddressDTO();

    Customer customer = customerRepository.findById(dto.getCustomer())
      .orElseThrow(() -> new RuntimeException("Customer not found"));
    Stock store = stockRepository.findById(dto.getStore())
      .orElseThrow(() -> new RuntimeException("Store not found"));
    Supplier supplier = supplierRepository.findById(dto.getSupplier())
      .orElseThrow(() -> new RuntimeException("Supplier not found"));
    Shipping shipping = shippingRepository.findById(dto.getShipping())
      .orElseThrow(() -> new RuntimeException("Shipping not found"));

    if (!addressRepository.existsById(dto.getId())) {
      Address address = Address.builder()
        .id(dto.getId())
        .appartment(dto.getAppartment())
        .city(dto.getCity())
        .street(dto.getStreet())
        .zip(dto.getZip())
        .country(dto.getCountry())
        .state(dto.getState())
        .customer(customer)
        .store(store)
        .supplier(supplier)
        .shipping(shipping)
        .build();
      addressRepository.save(address);
    } else {
      log.warn("Address with ID {} already exists! Ignoring.", dto.getId());
    }
  }

  @EventHandler
  public void on(DeletedAddressEvent event) {
    log.debug("Handling CategoryDeletedEvent: {}", event.getId());

    addressRepository.findById(event.getId())
      .ifPresent(address -> {
        addressRepository.delete(address);
        log.info("Category deleted with ID: {}", event.getId());
      });
  }
}
