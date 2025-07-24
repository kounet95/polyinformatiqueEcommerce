package org.example.ecpolycommand.web;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.example.polyinformatiquecoreapi.commandEcommerce.*;
import org.example.polyinformatiquecoreapi.dtoEcommerce.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@RestController
@RequestMapping("/stock/command")

public class Stock {

    private final CommandGateway commandGateway;
    private final EventStore eventStore;

    public Stock(CommandGateway commandGateway, EventStore eventStore) {
        this.commandGateway = commandGateway;
        this.eventStore = eventStore;
    }

//  @PostMapping("/create-supply-with-stocks")
//  @PreAuthorize("hasAuthority('ADMIN')")
//  public CompletableFuture<String> createSupplyWithStocks(
//    @Valid @RequestBody CreateSupplyWithStockDTO input) {
//
//    String supplyId = UUID.randomUUID().toString();
//
//    SupplyDTO supplyDTO = SupplyDTO.builder()
//      .id(supplyId)
//      .name(input.getName())
//      .createdAt(input.getCreatedAt())
//      .stocksIds(new ArrayList<>())
//      .build();
//
//    CreateSupplyCommande createSupplyCommand = new CreateSupplyCommande(supplyId, supplyDTO);
//
//    List<CompletableFuture<Object>> stockCommands = input.getStocks().stream().map(stockInput -> {
//
//      String stockId = UUID.randomUUID().toString();
//      String addressId = UUID.randomUUID().toString();
//
//      // ✅ Crée l’adresse pour ce stock
//      AddressDTO addressDTO = AddressDTO.builder()
//        .id(addressId)
//        .street(stockInput.getStreet())
//        .city(stockInput.getCity())
//        .state(stockInput.getState())
//        .zip(stockInput.getZip())
//        .country(stockInput.getCountry())
//        .appartment(stockInput.getAppartment())
//        .links(stockInput.getLinks())
//        .build();
//
//      CreateAddressCommand createAddressCmd = new CreateAddressCommand(addressId, addressDTO);
//
//      // ✅ Crée le stock
//      StockDTO stockDTO = StockDTO.builder()
//        .id(stockId)
//        .supplyId(supplyId)
//        .productSizeId(stockInput.getProductSizeId())
//        .supplierId(stockInput.getSupplierId())
//        .purchasePrice(stockInput.getPurchasePrice())
//        .promoPrice(stockInput.getPromoPrice())
//        .quantity(stockInput.getQuantity())
//        .build();
//
//      AddStockCommand addStockCommand = new AddStockCommand(stockId, stockDTO);
//
//      // Crée le lien entre l’adresse et le stock
//      LinkAddressCommand linkAddressCmd = LinkAddressCommand.builder()
//        .addressId(addressId)
//        .targetType("STOCK")
//        .targetId(stockId)
//        .build();
//
//      return commandGateway.send(createAddressCmd)
//        .thenCompose(result -> commandGateway.send(addStockCommand))
//        .thenCompose(result -> commandGateway.send(linkAddressCmd));
//
//    }).toList();
//
//    return commandGateway.send(createSupplyCommand)
//      .thenCompose(result -> CompletableFuture.allOf(stockCommands.toArray(new CompletableFuture[0])))
//      .thenApply(done -> "Supply + Stocks + Addresses + Links created successfully");
//  }

  @GetMapping("/events/{aggregateId}")
    public Stream<?> eventsStream(@PathVariable String aggregateId) {
        return eventStore.readEvents(aggregateId).asStream();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler(Exception exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error: " + exception.getMessage());
    }
}
