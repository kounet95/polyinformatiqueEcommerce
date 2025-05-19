package org.example.ecpolycommand.web;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.example.ecpolycommand.service.ImageStorageService;
import org.example.polyinformatiquecoreapi.commandEcommerce.CreateProductCommand;
import org.example.polyinformatiquecoreapi.dtoEcommerce.ProductDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@RestController
@RequestMapping("/product/command")
@CrossOrigin
public class Product {

    private final CommandGateway commandGateway;
    private final EventStore eventStore;
    private final ImageStorageService imageStorageService;

    public Product(CommandGateway commandGateway, EventStore eventStore, ImageStorageService imageStorageService) {
        this.commandGateway = commandGateway;
        this.eventStore = eventStore;
        this.imageStorageService = imageStorageService;
    }

    @PostMapping(value = "/create", consumes = {"multipart/form-data"})
    public CompletableFuture<String> createProduct(
            @RequestPart("product") @Valid ProductDTO product,
            @RequestPart(value = "media", required = false) MultipartFile mediaFile
    ) {
        String productId = UUID.randomUUID().toString();

        // Upload de média si présent
        if (mediaFile != null && !mediaFile.isEmpty()) {
            try {
                String imageUrl = imageStorageService.uploadImage(mediaFile);
                product.setImageUrl(imageUrl);
            } catch (IOException e) {
                CompletableFuture<String> failed = new CompletableFuture<>();
                failed.completeExceptionally(
                        new RuntimeException("Erreur lors de l'upload de l'image: " + e.getMessage())
                );
                return failed;
            }
        }

        ProductDTO productDTO = new ProductDTO(
                productId,
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getCreatedAt(),
                product.getClosedAt(),
                product.getSubcategoryId(),
                product.getSocialGroupId(),
                product.getImageUrl(),
                product.getIsActive()
        );
        CreateProductCommand command = new CreateProductCommand(productId, productDTO);
        return commandGateway.send(command);
    }

    @GetMapping("/events/{aggregateId}")
    public Stream<?> eventsStream(@PathVariable String aggregateId) {
        return eventStore.readEvents(aggregateId).asStream();
    }

    @PostMapping("/upload-image")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            String imageUrl = imageStorageService.uploadImage(file);
            return ResponseEntity.ok(imageUrl);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Image upload failed: " + e.getMessage());
        }
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler(Exception exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(" Error: " + exception.getMessage());
    }
}