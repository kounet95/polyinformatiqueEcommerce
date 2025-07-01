package org.example.ecpolycommand.web;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.example.polyinformatiquecoreapi.commandEcommerce.*;
import org.example.polyinformatiquecoreapi.dtoEcommerce.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@RestController
@RequestMapping("/supplier/command")
public class Supplier {

    private final CommandGateway commandGateway;
    private final EventStore eventStore;

    public Supplier(CommandGateway commandGateway, EventStore eventStore) {
        this.commandGateway = commandGateway;
        this.eventStore = eventStore;
    }

    @PostMapping("/create")
    public CompletableFuture<String> createSupplier(@Valid @RequestBody SupplierDTO supplier) {
        String supplierId = UUID.randomUUID().toString();
        SupplierDTO supplierDTO = new SupplierDTO(
                supplierId,
                supplier.getFullname(),
                supplier.getEmail(),
                supplier.getPersonToContact()
        );
        CreateSupplierCommand command = new CreateSupplierCommand(supplierId, supplierDTO);
        return commandGateway.send(command);
    }

  @PostMapping("/create-with-address")
  @PreAuthorize("hasAuthority('ADMIN')")
  public CompletableFuture<String> createSupplierWithAddress(
    @Valid @RequestBody CreateSupplierWithAddressDTO input,
    JwtAuthenticationToken jwtAuth) {

    //  ID unique pour l’adresse
    String addressId = UUID.randomUUID().toString();

    // ID unique pour le stock
    String supplierId = UUID.randomUUID().toString();

    //  Crée la commande d’adresse
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

    // Crée la commande supplier
      SupplierDTO supplierDTO= SupplierDTO.builder()
        .id(supplierId)
        .fullname(input.getFullname())
        .email(input.getEmail())
        .personToContact(input.getPersonToContact())
      .build();

   CreateSupplierCommand addSupplierCommand = new CreateSupplierCommand(supplierId,supplierDTO);

    //  Crée la commande de liaison
    LinkAddressCommand linkAddressCmd = LinkAddressCommand.builder()
      .addressId(addressId)
      .targetType("SUPPLIER")
      .targetId(supplierId)
      .build();

    return commandGateway.send(createAddressCmd)
      .thenCompose(addressResult -> commandGateway.send(addSupplierCommand))
      .thenCompose(supplierResult -> commandGateway.send(linkAddressCmd))
      .thenApply(linkResult -> "Supplier + Address created & linked successfully");
  }



  @DeleteMapping("/{supplierId}")
     public CompletableFuture<String> deleteSupplier(@PathVariable String supplierId) {
    DeleteSupplierCommand command = new DeleteSupplierCommand(supplierId);
    return commandGateway.send(command);
  }
    @GetMapping("/events/{aggregateId}")
    public Stream<?> eventsStream(@PathVariable String aggregateId) {
        return eventStore.readEvents(aggregateId).asStream();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler(Exception exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(" Error: " + exception.getMessage());
    }
}
