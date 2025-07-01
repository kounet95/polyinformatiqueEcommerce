package org.example.ecpolycommand.web;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.example.polyinformatiquecoreapi.commandEcommerce.*;
import org.example.polyinformatiquecoreapi.dtoEcommerce.AddressDTO;
import org.example.polyinformatiquecoreapi.dtoEcommerce.CreateCustomerWithAddressDTO;
import org.example.polyinformatiquecoreapi.dtoEcommerce.CustomerEcommerceDTO;
import org.example.polyinformatiquecoreapi.dtoEcommerce.CustomerWithAddressDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@RestController
@RequestMapping("/customer/command")

public class Customer {

    private final CommandGateway commandGateway;
    private final EventStore eventStore;

    public Customer(CommandGateway commandGateway, EventStore eventStore) {
        this.commandGateway = commandGateway;
        this.eventStore = eventStore;
    }

  @PostMapping("/create-with-address")
  @PreAuthorize("hasAuthority('ADMIN')")
  public CompletableFuture<String> createCustomerWithAddress(
    @Valid @RequestBody CreateCustomerWithAddressDTO input,
    JwtAuthenticationToken jwtAuth) {

    // ID unique pour l’adresse
    String addressId = UUID.randomUUID().toString();

    // L’Id du client vient du JWT
    String customerId = jwtAuth.getToken().getClaim("sub");

    // Crée la commande d’adresse (avec constructeur)
    AddressDTO addressDTO = AddressDTO.builder()
      .id(addressId)
      .street(input.getStreet())
      .city(input.getCity())
      .state(input.getState())
      .zip(input.getZip())
      .country(input.getCountry())
      .appartment(input.getAppartment())
      .links(input.getLinks())
      .build();

    CreateAddressCommand createAddressCmd = new CreateAddressCommand(addressId, addressDTO);

    CustomerEcommerceDTO customerDTO = CustomerEcommerceDTO.builder()
      .id(customerId)
      .firstname(input.getFirstName())
      .lastname(input.getLastName())
      .email(input.getEmail())
      .phone(input.getPhone())
      .build();

    CreateCustomerCommand createCustomerCmd = new CreateCustomerCommand(customerDTO);

    LinkAddressCommand linkAddressCmd = LinkAddressCommand.builder()
      .addressId(addressId)      // cle pour l'aggregate
      .targetType("CUSTOMER")    // le type cible
      .targetId(customerId)      // l'entite cible
      .build();

    return commandGateway.send(createAddressCmd)
      .thenCompose(addressResult -> commandGateway.send(createCustomerCmd))
      .thenCompose(customerResult -> commandGateway.send(linkAddressCmd))
      .thenApply(linkResult -> "Customer + Address created & linked successfully");
  }


  @GetMapping("/events/{aggregateId}")
    public Stream<?> eventsStream(@PathVariable String aggregateId) {
        return eventStore.readEvents(aggregateId).asStream();
    }

    @DeleteMapping("/delete/{customerId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public CompletableFuture<String> deleteCustomer(@PathVariable String customerId) {
        return commandGateway.send(new DeleteCustomerCommand(customerId));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler(Exception exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(" Error: " + exception.getMessage());
    }

  @PutMapping("/update/{id}")
  @PreAuthorize("hasAuthority('ADMIN')")
  public CompletableFuture<String> updateCustomer(
    @Valid @RequestBody CustomerEcommerceDTO author,
    @PathVariable String id) {
      CustomerEcommerceDTO customerDTO = new CustomerEcommerceDTO(
              id,
              author.getFirstname(),
              author.getEmail(),
              author.getLastname(),
              author.getPhone(),
              author.getCreatedAt()
      );
      CustomerUpdatedCommand command = new CustomerUpdatedCommand(id, customerDTO);
      return commandGateway.send(command);
  }

}
