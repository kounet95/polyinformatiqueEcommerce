package org.example.ecpolycommand.web;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.example.polyinformatiquecoreapi.commandEcommerce.*;
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
  public CompletableFuture<String> createCustomerWithAddress(@Valid @RequestBody CustomerWithAddressDTO input,
                                                             JwtAuthenticationToken jwtAuth) {
    String userId = jwtAuth.getToken().getClaim("sub");

    CustomerEcommerceDTO customerDTO = new CustomerEcommerceDTO(
      userId,
      input.getFirstname(),
      input.getEmail(),
      input.getLastname(),
      input.getPhone(),
      LocalDateTime.now()
    );

    CreateCustomerCommand cmd = new CreateCustomerCommand(customerDTO);

    return commandGateway.send(cmd).thenCompose(result -> {
      String addressId = UUID.randomUUID().toString();
      CreateAddressCommand addrCmd = new CreateAddressCommand(addressId, input.getAddress());
      return commandGateway.send(addrCmd)
        .thenCompose(addr -> {
          LinkAddressCommand linkCmd = new LinkAddressCommand("customer", userId, addressId);
          return commandGateway.send(linkCmd);
        })
        .thenApply(done -> "Customer + Address + Link OK");
    });
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
