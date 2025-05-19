package org.example.polyinformatiquecoreapi.controller;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.example.polyinformatiquecoreapi.commandEcommerce.ConfirmOrderCommand;
import org.example.polyinformatiquecoreapi.commandEcommerce.CreateOrderCommand;
import org.example.polyinformatiquecoreapi.commandEcommerce.UpdateOrderStatusCommand;
import org.example.polyinformatiquecoreapi.dtoEcommerce.OrderDTO;
import org.example.polyinformatiquecoreapi.dtoEcommerce.OrderStatusDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final CommandGateway commandGateway;

    public OrderController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping
    public CompletableFuture<ResponseEntity<String>> createOrder(@RequestBody OrderDTO orderDTO) {
        String orderId = UUID.randomUUID().toString();
        
        // Create a new DTO with the generated ID and current timestamp
        OrderDTO completeOrderDTO = new OrderDTO(
                orderId,
                orderDTO.getCustomerId(),
                LocalDateTime.now().toString(),
                "CREATED", // Initial status
                orderDTO.getPaymentMethod(),
                orderDTO.getTotal(),
                UUID.randomUUID().toString() // Generate a random barcode
        );
        
        CreateOrderCommand command = new CreateOrderCommand(orderId, completeOrderDTO);

        return commandGateway.send(command)
                .thenApply(result -> new ResponseEntity<>("Order created with ID: " + orderId, HttpStatus.CREATED))
                .exceptionally(ex -> new ResponseEntity<>("Error creating order: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @PutMapping("/{orderId}/confirm")
    public CompletableFuture<ResponseEntity<String>> confirmOrder(@PathVariable String orderId) {
        ConfirmOrderCommand command = new ConfirmOrderCommand(orderId);

        return commandGateway.send(command)
                .thenApply(result -> new ResponseEntity<>("Order confirmed with ID: " + orderId, HttpStatus.OK))
                .exceptionally(ex -> new ResponseEntity<>("Error confirming order: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @PutMapping("/{orderId}/status")
    public CompletableFuture<ResponseEntity<String>> updateOrderStatus(
            @PathVariable String orderId,
            @RequestBody OrderStatusDTO statusDTO) {
        
        UpdateOrderStatusCommand command = new UpdateOrderStatusCommand(
                orderId,
                statusDTO.getBarcode(),
                statusDTO.getStatus()
        );

        return commandGateway.send(command)
                .thenApply(result -> new ResponseEntity<>("Order status updated for ID: " + orderId, HttpStatus.OK))
                .exceptionally(ex -> new ResponseEntity<>("Error updating order status: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR));
    }
}