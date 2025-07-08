package org.example.ecpolycommand.web;

import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.example.polyinformatiquecoreapi.commandEcommerce.DelikerProductCommand;
import org.example.polyinformatiquecoreapi.commandEcommerce.LikerProductCommand;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
@RestController
@RequestMapping("/api/like")
@RequiredArgsConstructor
public class LikeController {

  private final CommandGateway commandGateway;

  @PostMapping("/{productId}/like")
  public CompletableFuture<ResponseEntity<String>> likeProduct(
    @PathVariable String productId,
    Principal principal
  ) {
    String customerId = principal.getName();
    String id = UUID.randomUUID().toString();

    LikerProductCommand command = new LikerProductCommand(id, customerId, productId);

    return commandGateway.send(command)
      .thenApply(result -> ResponseEntity.ok("Product liked!"));
  }

  @PostMapping("/{productId}/unlike")
  public CompletableFuture<ResponseEntity<String>> unlikeProduct(
    @PathVariable String productId,
    Principal principal
  ) {
    String customerId = principal.getName();
    String id = UUID.randomUUID().toString();

    DelikerProductCommand command = new DelikerProductCommand(id, productId, customerId);

    return commandGateway.send(command)
      .thenApply(result -> ResponseEntity.ok("Product unliked!"));
  }

}
