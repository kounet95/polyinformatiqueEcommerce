package org.example.ecpolycommand.web;

import jakarta.validation.Valid;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.example.polyinformatiquecoreapi.commandEcommerce.*;
import org.example.polyinformatiquecoreapi.dtoEcommerce.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/supply/command")
public class SupplyController {

  private final CommandGateway commandGateway;
  private final EventStore eventStore;

  public SupplyController(CommandGateway commandGateway, EventStore eventStore) {
    this.commandGateway = commandGateway;
    this.eventStore = eventStore;
  }

  @PostMapping("/create-with-stock-and-address")
  @PreAuthorize("hasAuthority('ADMIN')")
  public CompletableFuture<String> createSupplyWithStock(
    @Valid @RequestBody CreateSupplyWithStockDTO input) {

    if (input.getStocks() == null || input.getStocks().isEmpty()) {
      throw new IllegalArgumentException("At least one stock must be provided");
    }

    // ID unique pour le Supply
    String supplyId = input.getSupplyId() != null ? input.getSupplyId() : UUID.randomUUID().toString();

    // Liste pour stocker tous les IDs de stocks créés
    List<String> stockIds = new ArrayList<>();
    List<CompletableFuture<Object>> stockCommands = new ArrayList<>();

    // Parcourt chaque stock et crée un AddStockCommand
    for (StockDTO stockInput : input.getStocks()) {
      if (stockInput.getPurchasePrice() == null) {
        throw new IllegalArgumentException("purchasePrice must not be null");
      }
      if (stockInput.getPromoPrice() == null) {
        throw new IllegalArgumentException("promoPrice must not be null");
      }
      if (stockInput.getQuantity() == null) {
        throw new IllegalArgumentException("quantity must not be null");
      }

      String stockId = UUID.randomUUID().toString();
      stockIds.add(stockId);

      StockDTO stockDTO = StockDTO.builder()
        .id(stockId)
        .supplyId(supplyId)
        .productSizeId(stockInput.getProductSizeId())
        .supplierId(stockInput.getSupplierId())
        .purchasePrice(stockInput.getPurchasePrice())
        .promoPrice(stockInput.getPromoPrice())
        .quantity(stockInput.getQuantity())
        .build();

      AddStockCommand addStockCmd = new AddStockCommand(stockId, stockDTO);
      stockCommands.add(commandGateway.send(addStockCmd));
    }

    // ID unique pour l'Address
    String addressId = input.getAddressId() != null ? input.getAddressId() : UUID.randomUUID().toString();

    // --- Crée le Supply ---
    SupplyDTO supplyDTO = SupplyDTO.builder()
      .id(supplyId)
      .name(input.getName())
      .createdAt(input.getCreatedAt())
      .stocksIds(stockIds)
      .build();

    CreateSupplyCommande createSupplyCmd = new CreateSupplyCommande(supplyId, supplyDTO);

    // --- Crée l'Address ---
    AddressDTO addressDTO = AddressDTO.builder()
      .id(addressId)
      .street(input.getStreet())
      .city(input.getCity())
      .state(input.getState())
      .zip(input.getZip())
      .country(input.getCountry())
      .appartment(input.getAppartment())
      .links(input.getLinks())
      .build();

    CreateAddressCommand createAddressCmd = new CreateAddressCommand(addressId, addressDTO);

    // --- Pipeline ---
    return commandGateway.send(createSupplyCmd)
      .thenCompose(res -> {
        // Envoie tous les AddStockCommand en parallèle
        return CompletableFuture.allOf(stockCommands.toArray(new CompletableFuture[0]));
      })
      .thenCompose(res -> {
        // Crée l'adresse
        return commandGateway.send(createAddressCmd);
      })
      .thenCompose(res -> {
        // Crée les liens pour chaque stock
        List<CompletableFuture<Object>> linkCommands = new ArrayList<>();
        for (String stockId : stockIds) {
          LinkAddressCommand linkAddressCmd = LinkAddressCommand.builder()
            .addressId(addressId)
            .targetType("STOCK")
            .targetId(stockId)
            .build();
          linkCommands.add(commandGateway.send(linkAddressCmd));
        }
        return CompletableFuture.allOf(linkCommands.toArray(new CompletableFuture[0]));
      })
      .thenApply(res -> "Supply + Stocks + Address + Links created successfully");
  }

}
