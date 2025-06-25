package org.example.ecpolycommand.web;


import jakarta.validation.Valid;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.example.polyinformatiquecoreapi.commandEcommerce.CreateAddressCommand;
import org.example.polyinformatiquecoreapi.commandEcommerce.CreateCategoryCommand;
import org.example.polyinformatiquecoreapi.commandEcommerce.DeleteAddressCommand;
import org.example.polyinformatiquecoreapi.commandEcommerce.DeleteCategoryCommand;
import org.example.polyinformatiquecoreapi.dtoEcommerce.AddressDTO;
import org.example.polyinformatiquecoreapi.dtoEcommerce.CategoryDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@RestController
@RequestMapping("/address")
public class AddressController {

  private final CommandGateway commandGateway;
  private final EventStore eventStore;

  public AddressController(CommandGateway commandGateway, EventStore eventStore) {
    this.commandGateway = commandGateway;
    this.eventStore = eventStore;
  }

  @PostMapping("/create")
  @PreAuthorize("hasAuthority('ADMIN')")
  public CompletableFuture<String> createAddress(@Valid @RequestBody AddressDTO address) {
    String addressId = UUID.randomUUID().toString();
    AddressDTO addressDTO = new AddressDTO(
      addressId,
      address.getStreet(),
      address.getCity(),
      address.getState(),
      address.getZip(),
      address.getCountry(),
      address.getAppartment(),
      address.getCustomer(),
      address.getStore(),
      address.getSupplier(),
      address.getShipping()
    );
    CreateAddressCommand command = new CreateAddressCommand(addressId, addressDTO);
    return commandGateway.send(command);
  }

  @GetMapping("/events/{aggregateId}")
  public Stream<?> eventsStream(@PathVariable String aggregateId) {
    return eventStore.readEvents(aggregateId).asStream();
  }

  @DeleteMapping("/delete/{addressId}")
  @PreAuthorize("hasAuthority('ADMIN')")
  public CompletableFuture<String> deleteAddress(@PathVariable String addressId) {
    return commandGateway.send(new DeleteAddressCommand(addressId));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> exceptionHandler(Exception exception) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
      .body(" Error: " + exception.getMessage());
  }
}
