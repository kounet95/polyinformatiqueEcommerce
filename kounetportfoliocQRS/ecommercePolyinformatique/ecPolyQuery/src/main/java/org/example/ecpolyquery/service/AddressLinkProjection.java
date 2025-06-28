package org.example.ecpolyquery.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.example.ecpolyquery.entity.Address;
import org.example.ecpolyquery.entity.AddressLink;
import org.example.ecpolyquery.repos.AddressLinkRepository;
import org.example.ecpolyquery.repos.AddressRepository;

import org.example.polyinformatiquecoreapi.eventEcommerce.AddressLinkedEvent;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class AddressLinkProjection {

  private final AddressLinkRepository addressLinkRepository;
  private final AddressRepository addressRepository;

  @EventHandler

  public void on(AddressLinkedEvent event) {
    addressRepository.findById(event.getAddressId()).ifPresent(address -> {
      AddressLink link = AddressLink.builder()
        .targetType(event.getTargetType())
        .targetId(event.getTargetId())
        .address(address)
        .build();
      addressLinkRepository.save(link);
      log.info("Address linked: {} to {} {}", event.getAddressId(), event.getTargetType(), event.getTargetId());
    });
  }


}
