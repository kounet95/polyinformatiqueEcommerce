package org.example.polyinformatiquecoreapi.controller;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.example.polyinformatiquecoreapi.commandEcommerce.CreateCustomerCommand;
import org.example.polyinformatiquecoreapi.commandEcommerce.DeleteCustomerCommand;
import org.example.polyinformatiquecoreapi.dtoEcommerce.CustomerEcommerceDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CommandGateway commandGateway;

    public CustomerController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping
    public CompletableFuture<ResponseEntity<String>> createCustomer(@RequestBody CustomerEcommerceDTO customerDTO) {
        String customerId = UUID.randomUUID().toString();
        
        // Create a new DTO with the generated ID and current timestamp
        CustomerEcommerceDTO completeCustomerDTO = new CustomerEcommerceDTO(
                customerId,
                customerDTO.getFirstname(),
                customerDTO.getLastname(),
                customerDTO.getEmail(),
                customerDTO.getPhone(),
                customerDTO.getShippingAddress(),
                customerDTO.getBillingAddress(),
                LocalDateTime.now()
        );
        
        CreateCustomerCommand command = new CreateCustomerCommand(customerId, completeCustomerDTO);

        return commandGateway.send(command)
                .thenApply(result -> new ResponseEntity<>("Customer created with ID: " + customerId, HttpStatus.CREATED))
                .exceptionally(ex -> new ResponseEntity<>("Error creating customer: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @DeleteMapping("/{customerId}")
    public CompletableFuture<ResponseEntity<String>> deleteCustomer(@PathVariable String customerId) {
        DeleteCustomerCommand command = new DeleteCustomerCommand(customerId);

        return commandGateway.send(command)
                .thenApply(result -> new ResponseEntity<>("Customer deleted with ID: " + customerId, HttpStatus.OK))
                .exceptionally(ex -> new ResponseEntity<>("Error deleting customer: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR));
    }
}