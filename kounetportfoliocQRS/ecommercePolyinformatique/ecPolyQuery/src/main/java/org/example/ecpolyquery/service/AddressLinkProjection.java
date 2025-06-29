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
    eventBus.publish(GenericEventMessage.asEventMessage(
      new AddressLinkedEvent(cmd.getAddressId(),
        cmd.getTargetType(),
        cmd.getTargetId())
    ));
  }

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

    if (dto.getLinks() != null && !dto.getLinks().isEmpty()) {
      dto.getLinks().forEach(linkDTO -> {
        AddressLink link = AddressLink.builder()
          .targetType(linkDTO.getTargetType())
          .targetId(linkDTO.getTargetId())
          .address(address)
          .build();
        addressLinkRepository.save(link);
      });
    } else {
      log.info("No links provided for Address {}", dto.getId());
    }
  }



}
