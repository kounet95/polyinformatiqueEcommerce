package org.example.polyinformatiquecoreapi.controller;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.example.polyinformatiquecoreapi.commandEcommerce.CreateCategoryCommand;
import org.example.polyinformatiquecoreapi.commandEcommerce.DeleteCategoryCommand;
import org.example.polyinformatiquecoreapi.dtoEcommerce.CategoryDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CommandGateway commandGateway;

    public CategoryController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping
    public CompletableFuture<ResponseEntity<String>> createCategory(@RequestBody CategoryDTO categoryDTO) {
        String categoryId = UUID.randomUUID().toString();
        CreateCategoryCommand command = new CreateCategoryCommand(
                categoryId,
                categoryDTO
        );

        return commandGateway.send(command)
                .thenApply(result -> new ResponseEntity<>("Category created with ID: " + categoryId, HttpStatus.CREATED))
                .exceptionally(ex -> new ResponseEntity<>("Error creating category: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @DeleteMapping("/{categoryId}")
    public CompletableFuture<ResponseEntity<String>> deleteCategory(@PathVariable String categoryId) {
        DeleteCategoryCommand command = new DeleteCategoryCommand(categoryId);

        return commandGateway.send(command)
                .thenApply(result -> new ResponseEntity<>("Category deleted with ID: " + categoryId, HttpStatus.OK))
                .exceptionally(ex -> new ResponseEntity<>("Error deleting category: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR));
    }
}
