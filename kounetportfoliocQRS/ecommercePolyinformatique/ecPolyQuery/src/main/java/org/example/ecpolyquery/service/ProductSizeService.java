package org.example.ecpolyquery.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.example.ecpolyquery.entity.Product;
import org.example.ecpolyquery.entity.ProductSize;
import org.example.ecpolyquery.entity.Stock;
import org.example.ecpolyquery.repos.ProductRepository;
import org.example.ecpolyquery.repos.ProductSizeRepository;
import org.example.ecpolyquery.repos.StockRepository;
import org.example.polyinformatiquecoreapi.dtoEcommerce.ProductSizeDTO;
import org.example.polyinformatiquecoreapi.eventEcommerce.ProductSizeCreatedEvent;
import org.example.polyinformatiquecoreapi.eventEcommerce.ProductSizeDeletedEvent;
import org.example.polyinformatiquecoreapi.eventEcommerce.ProductSizeUpdatedEvent;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class ProductSizeService {

  private final ProductSizeRepository productSizeRepository;
  private final ProductRepository productRepository;
  private final StockRepository stockRepository;

  @EventHandler
  public void on(ProductSizeCreatedEvent event) {
    log.info("Product size created event received: {}", event.getId());

    ProductSizeDTO productSizeDTO = event.getProductSizeDTO();

    Product product = null;
    if (productSizeDTO.getProdId() != null) {
      product = productRepository.findById(productSizeDTO.getProdId())
        .orElseThrow(() -> new RuntimeException("Product not found with id: " + productSizeDTO.getProdId()));
    }


    List<Stock> stockIds = null;

    if (productSizeDTO.getStockIds() != null && !productSizeDTO.getStockIds().isEmpty()) {
      stockIds = stockRepository.findAllById(productSizeDTO.getStockIds());
    }

    ProductSize productSize = ProductSize.builder()
      .id(event.getId())
      .size(productSizeDTO.getSizeProd())
      .productId(product)
      .price(productSizeDTO.getPrice())
      .promoPrice(productSizeDTO.getPricePromo())
      .frontImage(productSizeDTO.getFrontUrl())
      .backImage(productSizeDTO.getBackUrl())
      .leftImage(productSizeDTO.getLeftUrl())
      .rightmage(productSizeDTO.getRightUrl())

      .build();

    productSizeRepository.save(productSize);
    log.info("Product size saved with ID: {}", productSize.getId());
  }

  @EventHandler
  public void on(ProductSizeDeletedEvent event) {
    log.info("Product size deleted event received: {}", event.getId());
    productSizeRepository.findById(event.getId()).ifPresent(productSize -> {
      productSizeRepository.delete(productSize);
      log.info("Product size deleted with ID: {}", event.getId());
    });
  }

  @EventHandler
  public void on(ProductSizeUpdatedEvent event) {
    log.info("Product size updated event received: {}", event.getId());

    ProductSizeDTO productSizeDTO = event.getProductSizeDTO();

    productSizeRepository.findById(event.getId()).ifPresent(productSize -> {
      if (productSizeDTO.getProdId() != null) {
        Product product = productRepository.findById(productSizeDTO.getProdId())
          .orElseThrow(() -> new RuntimeException("Product not found with id: " + productSizeDTO.getProdId()));
        productSize.setProductId(product);
      }

      productSize.setSize(productSizeDTO.getSizeProd());
      productSize.setPrice(productSizeDTO.getPrice());
      productSize.setPromoPrice(productSizeDTO.getPricePromo());
      productSize.setFrontImage(productSizeDTO.getFrontUrl());
      productSize.setBackImage(productSizeDTO.getBackUrl());
      productSize.setLeftImage(productSizeDTO.getLeftUrl());
      productSize.setRightmage(productSizeDTO.getRightUrl());

      productSizeRepository.save(productSize);
      log.info("Product size updated with ID: {}", productSize.getId());
    });
  }

}
