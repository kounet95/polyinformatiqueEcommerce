package org.example.ecpolyquery.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.example.ecpolyquery.entity.*;
import org.example.ecpolyquery.repos.*;
import org.example.polyinformatiquecoreapi.dtoEcommerce.AddressDTO;

import org.example.polyinformatiquecoreapi.dtoEcommerce.CreatedAddressEvent;
import org.example.polyinformatiquecoreapi.eventEcommerce.AddressLinkedEvent;
import org.example.polyinformatiquecoreapi.eventEcommerce.DeletedAddressEvent;
import org.example.polyinformatiquecoreapi.eventEcommerce.UpdatedAddressEvent;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor
public class AddressService {

  private final AddressRepository addressRepository;
  private final AddressLinkRepository addressLinkRepository;



  @EventHandler
  public void on(CreatedAddressEvent event) {
    AddressDTO dto = event.getAddressDTO();

    // Ensure appartment is not null to avoid database constraint violation
    String appartment = dto.getAppartment();
    if (appartment == null) {
      appartment = "";
    }

    Address address = Address.builder()
      .id(dto.getId())
      .street(dto.getStreet())
      .city(dto.getCity())
      .state(dto.getState())
      .zip(dto.getZip())
      .country(dto.getCountry())
      .appartment(appartment)
      .build();

    addressRepository.save(address);

    if (dto.getLinks() != null) {
      dto.getLinks().forEach(linkDTO -> {
        AddressLink link = AddressLink.builder()
          .targetType(linkDTO.getTargetType())
          .targetId(linkDTO.getTargetId())
          .address(address)
          .build();
        addressLinkRepository.save(link);
      });
    }
  }

  @EventHandler
  public void on(AddressLinkedEvent event) {
    addressRepository.findById(event.getAddressId()).ifPresentOrElse(address -> {
      AddressLink link = AddressLink.builder()
        .targetType(event.getTargetType())
        .targetId(event.getTargetId())
        .address(address)
        .build();

      addressLinkRepository.save(link);
      log.info("✅ Address link saved: {} -> {} {}", address.getId(), event.getTargetType(), event.getTargetId());
    }, () -> {
      log.warn("⚠️ Address not found yet: {} -> {} {}", event.getAddressId(), event.getTargetType(), event.getTargetId());
      // Create a placeholder address with the given ID
      Address placeholderAddress = Address.builder()
        .id(event.getAddressId())
        .street("Pending")
        .city("Pending")
        .state("Pending")
        .zip("Pending")
        .country("Pending")
        .appartment("")
        .build();
      addressRepository.save(placeholderAddress);

      // Create the link with the placeholder address
      AddressLink link = AddressLink.builder()
        .targetType(event.getTargetType())
        .targetId(event.getTargetId())
        .address(placeholderAddress)
        .build();
      addressLinkRepository.save(link);
      log.info("✅ Created placeholder address and link: {} -> {} {}",
               placeholderAddress.getId(), event.getTargetType(), event.getTargetId());
    });
  }

  public Address createAddressAndLink(AddressDTO dto, String targetType, String targetId) {
    // Ensure appartment is not null to avoid database constraint violation
    String appartment = dto.getAppartment();
    if (appartment == null) {
      appartment = ""; // Default to empty string if null
    }

    Address address = Address.builder()
      .id(dto.getId())
      .appartment(appartment)
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

        // Ensure appartment is not null to avoid database constraint violation
        String appartment = dto.getAppartment();
        if (appartment == null) {
          appartment = ""; // Default to empty string if null
        }
        address.setAppartment(appartment);
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
