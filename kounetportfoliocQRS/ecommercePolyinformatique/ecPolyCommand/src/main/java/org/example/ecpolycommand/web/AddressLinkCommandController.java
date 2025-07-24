package org.example.ecpolycommand.web;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.example.polyinformatiquecoreapi.commandEcommerce.LinkAddressCommand;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/address-link")
@AllArgsConstructor
public class AddressLinkCommandController {

  private final CommandGateway commandGateway;

  @PostMapping
  public CompletableFuture<ResponseEntity<String>> linkAddress(@RequestBody LinkAddressRequest request) {
    LinkAddressCommand cmd = new LinkAddressCommand(
      request.getTargetType(),
      request.getTargetId(),
      request.getAddressId()
    );
    return commandGateway.send(cmd)
      .thenApply(result -> ResponseEntity.ok("Address linked successfully!"))
      .exceptionally(ex -> ResponseEntity.badRequest().body(ex.getMessage()));
  }

  @Data
  public static class LinkAddressRequest {
    private String targetType;
    private String targetId;
    private String addressId;
  }
}
