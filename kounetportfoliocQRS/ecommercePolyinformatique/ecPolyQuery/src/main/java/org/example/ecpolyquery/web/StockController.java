package org.example.ecpolyquery.web;

import lombok.AllArgsConstructor;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.example.ecpolyquery.entity.ProductSize;
import org.example.ecpolyquery.entity.Stock;
import org.example.ecpolyquery.query.GetAllStocksQuery;
import org.example.ecpolyquery.query.GetNewArrivalsStockQuery;
import org.example.ecpolyquery.query.GetOnSaleStockQuery;
import org.example.ecpolyquery.query.GetStockByIdQuery;
import org.example.ecpolyquery.repos.StockRepository;
import org.example.polyinformatiquecoreapi.dtoEcommerce.ProductSizeDTO;
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
    public CompletableFuture<List<Stock>> getAllStocks() {
        return queryGateway.query(new GetAllStocksQuery(),
                ResponseTypes.multipleInstancesOf(Stock.class));
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





}
