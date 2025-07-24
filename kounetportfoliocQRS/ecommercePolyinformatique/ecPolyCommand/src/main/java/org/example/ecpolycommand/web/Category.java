package org.example.ecpolycommand.web;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.example.polyinformatiquecoreapi.commandEcommerce.CreateCategoryCommand;
import org.example.polyinformatiquecoreapi.commandEcommerce.DeleteCategoryCommand;
import org.example.polyinformatiquecoreapi.commandEcommerce.UpdateCategoryCommand;
import org.example.polyinformatiquecoreapi.dtoEcommerce.CategoryDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@RestController
@RequestMapping("/category")
public class Category {

  private final CommandGateway commandGateway;
  private final EventStore eventStore;

  public Category(CommandGateway commandGateway, EventStore eventStore) {
    this.commandGateway = commandGateway;
    this.eventStore = eventStore;
  }

  @PostMapping("/create")
  @PreAuthorize("hasAuthority('ADMIN')")
  public CompletableFuture<String> createCategory(@Valid @RequestBody CategoryDTO category) {
    String categoryId = UUID.randomUUID().toString();
    CategoryDTO categoryDTO = new CategoryDTO(
      categoryId,
      category.getName()
    );
    CreateCategoryCommand command = new CreateCategoryCommand(categoryId, categoryDTO);
    return commandGateway.send(command);
  }

  @PutMapping("/update/{categoryId}")
  @PreAuthorize("hasAuthority('ADMIN')")
  public CompletableFuture<String> updateCategory(@PathVariable String categoryId, @Valid @RequestBody CategoryDTO category) {
    // On conserve l'identifiant depuis l'URL
    CategoryDTO updatedCategoryDTO = new CategoryDTO(categoryId, category.getName());
    UpdateCategoryCommand command = new UpdateCategoryCommand(categoryId, updatedCategoryDTO);
    return commandGateway.send(command);
  }

  @DeleteMapping("/delete/{categoryId}")
  @PreAuthorize("hasAuthority('ADMIN')")
  public CompletableFuture<String> deleteCategory(@PathVariable String categoryId) {
    return commandGateway.send(new DeleteCategoryCommand(categoryId));
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
