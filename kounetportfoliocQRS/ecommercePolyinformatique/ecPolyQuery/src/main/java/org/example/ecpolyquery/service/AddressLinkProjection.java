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
  public void on(AddressLinkedEvent event) {
    addressRepository.findById(event.getAddressId()).ifPresentOrElse(address -> {
      AddressLink link = AddressLink.builder()
        .targetType(event.getTargetType())
        .targetId(event.getTargetId())
        .address(address)
        .build();
      addressLinkRepository.save(link);
      log.info("Link created: [{}-{}] -> {}", event.getTargetType(), event.getTargetId(), address.getId());
    }, () -> {
      log.error("Address ID {} not found for linking!", event.getAddressId());
    });
  }

}
