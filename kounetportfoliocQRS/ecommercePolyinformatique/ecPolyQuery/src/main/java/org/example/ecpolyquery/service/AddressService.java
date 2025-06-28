package org.example.ecpolyquery.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.example.ecpolyquery.entity.*;
import org.example.ecpolyquery.repos.*;
import org.example.polyinformatiquecoreapi.dtoEcommerce.AddressDTO;

import org.example.polyinformatiquecoreapi.dtoEcommerce.CreatedAddressEvent;
import org.example.polyinformatiquecoreapi.eventEcommerce.DeletedAddressEvent;
import org.example.polyinformatiquecoreapi.eventEcommerce.UpdatedAddressEvent;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class AddressService {

  private final AddressRepository addressRepository;
  private final SupplierRepository supplierRepository;
  private final ShippingRepository shippingRepository;
  private final CustomerRepository customerRepository;
  private final StockRepository stockRepository;
  private final AddressLinkRepository addressLinkRepository;

  @EventHandler
  public void on(CreatedAddressEvent event) {
    AddressDTO dto = event.getAddressDTO();

    // Exemple basique: priorité CUSTOMER > SUPPLIER > STOCK > SHIPPING
    String targetType = null;
    String targetId = null;
    if (dto.getCustomer() != null) {
      targetType = "CUSTOMER";
      targetId = dto.getCustomer();
    } else if (dto.getSupplier() != null) {
      targetType = "SUPPLIER";
      targetId = dto.getSupplier();
    } else if (dto.getStore() != null) {
      targetType = "STOCK";
      targetId = dto.getStore();
    } else if (dto.getShipping() != null) {
      targetType = "SHIPPING";
      targetId = dto.getShipping();
    }

    if (targetType == null || targetId == null) {
      log.warn("CreatedAddressEvent: No targetType/targetId found in DTO {}", dto);
      return;
    }

    createAddressAndLink(dto, targetType, targetId);
    log.info("Address created and linked [{}/{}]: {}", targetType, targetId, dto.getId());
  }

  public Address createAddressAndLink(AddressDTO dto, String targetType, String targetId) {
    Address address = Address.builder()
      .id(dto.getId())
      .appartment(dto.getAppartment())
      .city(dto.getCity())
      .street(dto.getStreet())
      .zip(dto.getZip())
      .country(dto.getCountry())
      .state(dto.getState())
      .build();
    addressRepository.save(address);

    AddressLink link = AddressLink.builder()
      .targetType(targetType)
      .targetId(targetId)
      .address(address)
      .build();
    addressLinkRepository.save(link);

    return address;
  }

  public List<Address> getAddressesFor(String targetType, String targetId) {
    return addressLinkRepository.findByTargetTypeAndTargetId(targetType, targetId)
      .stream()
      .map(AddressLink::getAddress)
      .toList();
  }

  @EventHandler
  public void on(DeletedAddressEvent event) {
    log.debug("Handling DeletedAddressEvent: {}", event.getId());
    addressRepository.findById(event.getId())
      .ifPresent(address -> {
        addressRepository.delete(address);
        log.info("Address deleted with ID: {}", event.getId());
      });
  }

  @EventHandler
  public void on(UpdatedAddressEvent event) {
    log.debug("Handling UpdatedAddressEvent: {}", event.getId());
    AddressDTO dto = event.getAddressDTO();

    addressRepository.findById(event.getId())
      .ifPresent(address -> {
        // Update the address fields
        address.setStreet(dto.getStreet());
        address.setCity(dto.getCity());
        address.setState(dto.getState());
        address.setZip(dto.getZip());
        address.setCountry(dto.getCountry());
        address.setAppartment(dto.getAppartment());
        addressRepository.save(address);
        log.info("Address updated with ID: {}", dto.getId());
      });
    // Si besoin, mettre à jour les AddressLink (changement de cible)
    // Ex: Supprimer et recréer les liens selon les nouveaux targetType/targetId
  }
}
