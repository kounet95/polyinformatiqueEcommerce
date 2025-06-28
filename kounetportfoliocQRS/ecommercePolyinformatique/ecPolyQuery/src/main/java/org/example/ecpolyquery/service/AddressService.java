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

    Address address = Address.builder()
      .id(dto.getId())
      .street(dto.getStreet())
      .city(dto.getCity())
      .state(dto.getState())
      .zip(dto.getZip())
      .country(dto.getCountry())
      .appartment(dto.getAppartment())
      .build();

    addressRepository.save(address);

    // Pour chaque lien, créer une entrée AddressLink
    if (dto.getLinks() != null && !dto.getLinks().isEmpty()) {
      dto.getLinks().forEach(linkDTO -> {
        AddressLink link = AddressLink.builder()
          .targetType(linkDTO.getTargetType())
          .targetId(linkDTO.getTargetId())
          .address(address)
          .build();
        addressLinkRepository.save(link);
        log.info("Address linked to [{}/{}]: {}", linkDTO.getTargetType(), linkDTO.getTargetId(), dto.getId());
      });
    } else {
      log.warn("CreatedAddressEvent: Aucun lien fourni pour Address {}", dto.getId());
    }
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
    AddressDTO dto = event.getAddressDTO();

    addressRepository.findById(event.getId())
      .ifPresent(address -> {
        address.setStreet(dto.getStreet());
        address.setCity(dto.getCity());
        address.setState(dto.getState());
        address.setZip(dto.getZip());
        address.setCountry(dto.getCountry());
        address.setAppartment(dto.getAppartment());
        addressRepository.save(address);

        // 🔄 Re-créer les liens
        addressLinkRepository.deleteByAddress(address);

        if (dto.getLinks() != null && !dto.getLinks().isEmpty()) {
          dto.getLinks().forEach(linkDTO -> {
            AddressLink newLink = AddressLink.builder()
              .targetType(linkDTO.getTargetType())
              .targetId(linkDTO.getTargetId())
              .address(address)
              .build();
            addressLinkRepository.save(newLink);
          });
        }

        log.info("Address updated with new links: {}", dto.getId());
      });
  }
}
