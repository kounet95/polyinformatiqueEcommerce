package org.example.ecpolyquery.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.GenericEventMessage;
import org.example.ecpolyquery.entity.Address;
import org.example.ecpolyquery.entity.AddressLink;
import org.example.ecpolyquery.repos.AddressLinkRepository;
import org.example.ecpolyquery.repos.AddressRepository;

import org.example.polyinformatiquecoreapi.commandEcommerce.LinkAddressCommand;
import org.example.polyinformatiquecoreapi.dtoEcommerce.AddressDTO;
import org.example.polyinformatiquecoreapi.dtoEcommerce.CreatedAddressEvent;
import org.example.polyinformatiquecoreapi.eventEcommerce.AddressLinkedEvent;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class AddressLinkProjection {

  private final AddressLinkRepository addressLinkRepository;
  private final AddressRepository addressRepository;



  private final EventBus eventBus;



  @CommandHandler
  public void handle(LinkAddressCommand cmd) {
    try {
      log.debug("Handling LinkAddressCommand: addressId={}, targetType={}, targetId={}",
                cmd.getAddressId(), cmd.getTargetType(), cmd.getTargetId());

      eventBus.publish(GenericEventMessage.asEventMessage(
        new AddressLinkedEvent(cmd.getAddressId(),
          cmd.getTargetType(),
          cmd.getTargetId())
      ));

      log.info("Published AddressLinkedEvent for addressId={}, targetType={}, targetId={}",
               cmd.getAddressId(), cmd.getTargetType(), cmd.getTargetId());
    } catch (Exception e) {
      log.error("Error handling LinkAddressCommand: {}", e.getMessage(), e);
      throw e; // Rethrow to ensure the command is not acknowledged
    }
  }

  @EventHandler
  public void on(CreatedAddressEvent event) {
    try {
      log.debug("Handling CreatedAddressEvent: {}", event.getAddressDTO().getId());
      AddressDTO dto = event.getAddressDTO();

      // Check if address already exists to avoid duplicates
      if (addressRepository.existsById(dto.getId())) {
        log.info("Address with ID {} already exists, skipping creation", dto.getId());
        return;
      }

      // Ensure appartment is not null to avoid database constraint violation
      String appartment = dto.getAppartment();
      if (appartment == null) {
        appartment = ""; // Default to empty string if null
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
      log.info("Address created with ID: {}", dto.getId());

      // Create links if provided
      if (dto.getLinks() != null && !dto.getLinks().isEmpty()) {
        for (var linkDTO : dto.getLinks()) {
          try {
            AddressLink link = AddressLink.builder()
              .targetType(linkDTO.getTargetType())
              .targetId(linkDTO.getTargetId())
              .address(address)
              .build();
            addressLinkRepository.save(link);
            log.info("Address link created: {} -> {} {}",
                    dto.getId(), linkDTO.getTargetType(), linkDTO.getTargetId());
          } catch (Exception e) {
            log.error("Error creating link for address {}: {}", dto.getId(), e.getMessage(), e);
            // Continue with other links even if one fails
          }
        }
      } else {
        log.info("No links provided for Address {}", dto.getId());
      }
    } catch (Exception e) {
      log.error("Error processing CreatedAddressEvent for address ID {}: {}",
               event.getAddressDTO().getId(), e.getMessage(), e);
      throw e; // Rethrow to ensure the event is not acknowledged
    }
  }



}
