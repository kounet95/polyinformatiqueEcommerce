package org.example.ecpolyquery.web;

import lombok.AllArgsConstructor;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.example.ecpolyquery.entity.ProductSize;
import org.example.ecpolyquery.entity.Stock;
import org.example.ecpolyquery.query.*;
import org.example.ecpolyquery.repos.StockRepository;
import org.example.polyinformatiquecoreapi.dtoEcommerce.ProductSizeDTO;
import org.example.polyinformatiquecoreapi.dtoEcommerce.StockDTO;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/stocks")
@AllArgsConstructor
public class StockController {

  private final QueryGateway queryGateway;
  private final StockRepository stockRepository;

  @GetMapping
  public CompletableFuture<List<StockDTO>> getAllStocks(
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size) {

    GetAllStocksQuery query = new GetAllStocksQuery(page, size);

    return queryGateway.query(query,
      ResponseTypes.multipleInstancesOf(StockDTO.class));
  }

  @GetMapping("/{id}")
  public CompletableFuture<Stock> getStockById(@PathVariable String id) {
    return queryGateway.query(new GetStockByIdQuery(id),
      ResponseTypes.instanceOf(Stock.class));
  }

  @GetMapping("/new-arrivals")
  public CompletableFuture<List<ProductSizeDTO>> getNewArrivals(
    @RequestParam("since") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime since) {

    return queryGateway.query(
      new GetNewArrivalsStockQuery(since),
      ResponseTypes.multipleInstancesOf(Stock.class)
    ).thenApply(stocks -> stocks.stream().map(this::toProductSizeDTO).collect(Collectors.toList()));
  }

  @GetMapping("/by-product-size/{productSizeId}")
  public CompletableFuture<Stock> getStockByProductSizeId(@PathVariable String productSizeId) {
    return queryGateway.query(
      new GetStockByProductSizeIdQuery(productSizeId),
      ResponseTypes.instanceOf(Stock.class)
    );
  }

  @GetMapping("/on-sale")
  public CompletableFuture<List<ProductSizeDTO>> getOnSale() {
    return queryGateway.query(
      new GetOnSaleStockQuery(),
      ResponseTypes.multipleInstancesOf(Stock.class)
    ).thenApply(stocks -> stocks.stream().map(this::toProductSizeDTO).collect(Collectors.toList()));
  }

  private ProductSizeDTO toProductSizeDTO(Stock stock) {
    ProductSize ps = stock.getProductSize();
    return ProductSizeDTO.builder()
      .id(ps.getId())
      .prodId(ps.getProductId() != null ? ps.getProductId().getId() : null)
      .price(ps.getPrice())
      .pricePromo(ps.getPromoPrice())
      .frontUrl(ps.getFrontImage())
      .backUrl(ps.getBackImage())
      .leftUrl(ps.getLeftImage())
      .rightUrl(ps.getRightmage())
      .sizeProd(ps.getSize())
      .build();
  }

  @GetMapping("/productsize/{id}")
  public List<StockDTO> getStocksByProductSize(@PathVariable String id) {
    // Récupère tous les stocks liés au ProductSize
    return stockRepository.findByProductSizeId(id)
      .stream()
      .map(stock -> StockDTO.builder()
        .id(stock.getId())
        .productSizeId(stock.getProductSize() != null ? stock.getProductSize().getId() : null)
        .supplierId(stock.getSupplier() != null ? stock.getSupplier().getId() : null)
        .purchasePrice(stock.getPurchasePrice())
        .promoPrice(stock.getPromoPrice())
        .quantity(stock.getQuantity())
        .supplyId(stock.getSupply() != null ? stock.getSupply().getId() : null)
        .build()
      )
      .toList();
  }

  @PostMapping("/validate-availability")
  public CompletableFuture<Boolean> validateStockAvailability(@RequestBody List<StockValidationRequest> requests) {
    return CompletableFuture.supplyAsync(() -> {
      for (StockValidationRequest request : requests) {
        Stock stock = stockRepository.findById(request.getStockId()).orElse(null);
        if (stock == null || stock.getQuantity() < request.getRequestedQuantity()) {
          return false;
        }
      }
      return true;
    });
  }

  public static class StockValidationRequest {
    private String stockId;
    private int requestedQuantity;

    public StockValidationRequest() {}

    public StockValidationRequest(String stockId, int requestedQuantity) {
      this.stockId = stockId;
      this.requestedQuantity = requestedQuantity;
    }

    public String getStockId() { return stockId; }
    public void setStockId(String stockId) { this.stockId = stockId; }
    public int getRequestedQuantity() { return requestedQuantity; }
    public void setRequestedQuantity(int requestedQuantity) { this.requestedQuantity = requestedQuantity; }
  }


}
