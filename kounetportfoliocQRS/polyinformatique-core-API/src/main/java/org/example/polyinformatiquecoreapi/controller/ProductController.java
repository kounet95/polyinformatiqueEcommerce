package org.example.polyinformatiquecoreapi.controller;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.example.polyinformatiquecoreapi.commandEcommerce.CreateProductCommand;
import org.example.polyinformatiquecoreapi.commandEcommerce.DeleteProductCommand;
import org.example.polyinformatiquecoreapi.dtoEcommerce.ProductDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final CommandGateway commandGateway;

    public ProductController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping
    public CompletableFuture<ResponseEntity<String>> createProduct(@RequestBody ProductDTO productDTO) {
        String productId = UUID.randomUUID().toString();

        // Create a new DTO with the generated ID and current timestamp
        ProductDTO completeProductDTO = new ProductDTO(
                productId,
                productDTO.getName(),
                productDTO.getDescription(),
                productDTO.getPrice(),
                LocalDateTime.now().toString(),
                null, // closedAt is null for new products
                productDTO.getSubcategoryId(),
                productDTO.getSocialGroupId(),
                productDTO.getImageUrl(),
                true // new products are active by default
        );

        CreateProductCommand command = new CreateProductCommand(productId, completeProductDTO);

        return commandGateway.send(command)
                .thenApply(result -> new ResponseEntity<>("Product created with ID: " + productId, HttpStatus.CREATED))
                .exceptionally(ex -> new ResponseEntity<>("Error creating product: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @DeleteMapping("/{productId}")
    public CompletableFuture<ResponseEntity<String>> deleteProduct(@PathVariable String productId) {
        DeleteProductCommand command = new DeleteProductCommand(productId);

        return commandGateway.send(command)
                .thenApply(result -> new ResponseEntity<>("Product deleted with ID: " + productId, HttpStatus.OK))
                .exceptionally(ex -> new ResponseEntity<>("Error deleting product: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR));
    }
}
