package org.example.ecpolyquery.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.example.ecpolyquery.entity.Address;
import org.example.ecpolyquery.entity.Supplier;
import org.example.ecpolyquery.repos.AddressRepository;
import org.example.ecpolyquery.repos.SupplierRepository;
import org.example.ecpolyquery.query.GetAllSuppliersQuery;
import org.example.ecpolyquery.query.GetSupplierByIdQuery;
import org.example.polyinformatiquecoreapi.dtoEcommerce.SupplierDTO;
import org.example.polyinformatiquecoreapi.eventEcommerce.SupplierCreatedEvent;

import org.example.polyinformatiquecoreapi.eventEcommerce.SupplierDeletedEvent;
import org.example.polyinformatiquecoreapi.eventEcommerce.SupplierUpdatedEvent;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class SupplierService {

  private final SupplierRepository supplierRepository;
 private final AddressRepository addressRepository;
  @EventHandler
  public void on(SupplierCreatedEvent event) {
    log.debug("Handling SupplierCreatedEvent: {}", event.getId());
    SupplierDTO dto = event.getSupplierDTO();
    Address address = addressRepository.findById(event.getSupplierDTO().getAddressId()).orElse(null);
    if (!supplierRepository.existsById(event.getId())) {
      Supplier supplier = Supplier.builder()
        .id(event.getId())
        .fullname(dto.getFullname())
        .address(address)
        .email(dto.getEmail())
        .personToContact(dto.getPersonToContact())
        .build();
      supplierRepository.save(supplier);
      log.info("Supplier created with ID: {}", event.getId());
    } else {
      log.warn("Supplier with ID {} already exists! Ignoring.", event.getId());
    }
  }

  @EventHandler
  public void on(SupplierDeletedEvent event) {
    log.debug("Handling SupplierDeletedEvent: {}", event.getId());
    supplierRepository.findById(event.getId()).ifPresent(supplier -> {
      supplierRepository.delete(supplier);
      log.info("Supplier deleted with ID: {}", event.getId());
    });
  }

  @EventHandler
  public void on(SupplierUpdatedEvent event) {
    log.debug("Handling SupplierUpdatedEvent: {}", event.getId());
    SupplierDTO dto = event.getDto();
    Address address = addressRepository.findById(dto.getAddressId()).orElse(null);
    supplierRepository.findById(event.getId()).ifPresent(supplier -> {
      supplier.setFullname(dto.getFullname());
      supplier.setAddress(address);
      supplier.setEmail(dto.getEmail());
      supplier.setPersonToContact(dto.getPersonToContact());
      supplierRepository.save(supplier);
      log.info("Supplier updated with ID: {}", event.getId());
    });
  }

  @QueryHandler
  public List<Supplier> handle(GetAllSuppliersQuery query) {
    return supplierRepository.findAll();
  }

  @QueryHandler
  public Supplier handle(GetSupplierByIdQuery query) {
    return supplierRepository.findById(query.getId()).orElse(null);
  }
}
