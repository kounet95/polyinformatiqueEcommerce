package org.example.ecpolycommand.web;

import jakarta.validation.Valid;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.example.ecpolycommand.service.CloudinaryService;
import org.example.polyinformatiquecoreapi.commandEcommerce.CreateProductSizeCommand;
import org.example.polyinformatiquecoreapi.commandEcommerce.DeleteProductSizeCommand;
import org.example.polyinformatiquecoreapi.dtoEcommerce.ProductSizeDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@RestController
@RequestMapping("/productsize/command")
public class ProductSizeController {
  private final CommandGateway commandGateway;
  private final EventStore eventStore;

  private final CloudinaryService cloudinaryService;
  public ProductSizeController(
    CommandGateway commandGateway,
    EventStore eventStore, CloudinaryService cloudinaryService
  ) {
    this.commandGateway = commandGateway;
    this.eventStore = eventStore;
    this.cloudinaryService = cloudinaryService;
  }

  /**
   * Création d'un produitSize avec ou sans upload d'image.
   * on reçoit le produit et le fichier image dans le même POST multipart.
   */
  @PostMapping(value = "/create", consumes = {"multipart/form-data"})
  @PreAuthorize("hasAuthority('ADMIN')")
  public CompletableFuture<String> createProductSize(
    @RequestPart("productSize") @Valid ProductSizeDTO product,
    @RequestPart(value = "media", required = false) List<MultipartFile> mediaFiles
  ) {
    String productId = UUID.randomUUID().toString();

    try {
      if (mediaFiles != null && !mediaFiles.isEmpty()) {
        for (int i = 0; i < mediaFiles.size(); i++) {
          String imageUrl = cloudinaryService.uploadImage(mediaFiles.get(i));

          //on va cherher associer chaque image à un champ spécifique
          switch (i) {
            case 0 -> product.setFrontUrl(imageUrl);
            case 1 -> product.setBackUrl(imageUrl);
            case 2 -> product.setLeftUrl(imageUrl);
            case 3 -> product.setRightUrl(imageUrl);
            default -> {}
          }
        }
      }
    } catch (IOException e) {
      CompletableFuture<String> failed = new CompletableFuture<>();
      failed.completeExceptionally(
        new RuntimeException("Erreur lors de l'upload de l'image: " + e.getMessage())
      );
      return failed;
    }

    product.setId(productId);
    CreateProductSizeCommand command = new CreateProductSizeCommand(productId, product);
    return commandGateway.send(command);
  }

  @GetMapping("/events/{aggregateId}")
  public Stream<?> eventsStream(@PathVariable String aggregateId) {
    return eventStore.readEvents(aggregateId).asStream();
  }

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
    return commandGateway.send(new DeleteProductSizeCommand(id));
  }
}

