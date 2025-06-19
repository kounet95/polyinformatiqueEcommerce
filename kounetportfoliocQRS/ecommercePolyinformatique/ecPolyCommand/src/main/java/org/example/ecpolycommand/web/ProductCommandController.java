package org.example.ecpolycommand.web;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.example.ecpolycommand.service.ImageStorageService;
import org.example.polyinformatiquecoreapi.commandEcommerce.CreateProductCommand;
import org.example.polyinformatiquecoreapi.commandEcommerce.DeleteInvoiceCommand;
import org.example.polyinformatiquecoreapi.dtoEcommerce.ProductDTO;
import org.example.polyinformatiquecoreapi.eventEcommerce.ProductDeletedEvent;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@RestController
@RequestMapping("/product/command")
public class ProductCommandController {

  private final CommandGateway commandGateway;
  private final EventStore eventStore;
  private final ImageStorageService imageStorageService;

  public ProductCommandController(
    CommandGateway commandGateway,
    EventStore eventStore,
    ImageStorageService imageStorageService
  ) {
    this.commandGateway = commandGateway;
    this.eventStore = eventStore;
    this.imageStorageService = imageStorageService;
  }

  /**
   * Création un produit avec ou sans upload d'image.
   * on reçoit le produit et le fichier image dans le même POST multipart.
   */
  @PostMapping(value = "/create", consumes = {"multipart/form-data"})
  @PreAuthorize("hasAuthority('ADMIN')")
  public CompletableFuture<String> createProduct(
    @RequestPart("product") @Valid ProductDTO product,
    @RequestPart(value = "media", required = false) MultipartFile mediaFile
  ) {
    String productId = UUID.randomUUID().toString();

    // Upload l'image si présente
    if (mediaFile != null && !mediaFile.isEmpty()) {
      try {
        String models = imageStorageService.uploadImage(mediaFile);
        product.setModels(models);
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
      product.getProductSizes(),
      product.getCreatedAt(),
      product.getModels(),
      product.getSubcategoryId(),
      product.getSocialGroupId(),
      product.getIsActive()
    );

    CreateProductCommand command = new CreateProductCommand(productId, productDTO);
    return commandGateway.send(command);
  }

  /**
   * Récupère les événements d'un agrégat produit.
   */
  @GetMapping("/events/{aggregateId}")
  public Stream<?> eventsStream(@PathVariable String aggregateId) {
    return eventStore.readEvents(aggregateId).asStream();
  }

//  /**
//   * Upload d'une image seule (retourne directement l'URL Drive).
//   */
//  @PostMapping("/upload-image")
//  public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
//    try {
//      String imageUrl = imageStorageService.uploadImage(file);
//      return ResponseEntity.ok(imageUrl);
//    } catch (IOException e) {
//      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//        .body("Image upload failed: " + e.getMessage());
//    }
//  }

  /**
   * Gestion centralisée des exceptions.
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> exceptionHandler(Exception exception) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
      .body("Error: " + exception.getMessage());
  }

  @DeleteMapping("/delete/{id}")
  @PreAuthorize("hasAuthority('ADMIN')")
  public CompletableFuture<String> deleteCustomer(@PathVariable String id) {
    return commandGateway.send(new ProductDeletedEvent(id));
  }
}
