package org.example.ecpolyquery.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.example.ecpolyquery.entity.Product;
import org.example.ecpolyquery.entity.ProductSize;
import org.example.ecpolyquery.entity.Subcategory;
import org.example.ecpolyquery.repos.ProductRepository;
import org.example.ecpolyquery.repos.ProductSizeRepository;
import org.example.polyinformatiquecoreapi.dtoEcommerce.ProductSizeDTO;
import org.example.polyinformatiquecoreapi.eventEcommerce.ProductSizeCreatedEvent;
import org.example.polyinformatiquecoreapi.eventEcommerce.ProductSizeDeletedEvent;
import org.example.polyinformatiquecoreapi.eventEcommerce.ProductSizeUpdatedEvent;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class ProductSizeService {

  private final ProductSizeRepository productSizeRepository;
  private final ProductRepository productRepository;

  @EventHandler
  public void on(ProductSizeCreatedEvent event) {
    log.info("Product size created event received: {}", event.getId());

    ProductSizeDTO productSizeDTO = event.getProductSizeDTO();
    Product product = null;
    if (productSizeDTO.getProdId() != null) {
      product = productRepository.findById(productSizeDTO.getProdId())
        .orElseThrow(() -> new RuntimeException("Subcategory not found with id: " + productSizeDTO.getProdId()));
    }

    ProductSize productSize = ProductSize.builder()

      .id(event.getId())
      .size(event.getProductSizeDTO().getSizeProd())
      .productId(product)
      .price(event.getProductSizeDTO().getPrice())
      .promoPrice(event.getProductSizeDTO().getPricePromo())
      .urlImage(event.getProductSizeDTO().getImageUrl())
      .build();
    productSizeRepository.save(productSize);
    log.info("Product size saved event received: {}", productSize.getId());
  }

  @EventHandler
  public void on(ProductSizeDeletedEvent event) {

    log.info("Product size deleted event received: {}", event.getId());
    productSizeRepository.findById(event.getId()).ifPresent(productSize -> {
      productSizeRepository.delete(productSize);
      log.info("Product size deleted event received: {}", event.getId());
    });
  }

  public void on(ProductSizeUpdatedEvent event) {

    log.info("Product size updated event received: {}", event.getId());

    ProductSizeDTO productSizeDTO = event.getProductSizeDTO();
    productSizeRepository.findById(event.getId()).ifPresent(productSize -> {
      if (productSizeDTO.getProdId() != null) {
        Product product = productRepository.findById(productSizeDTO.getProdId())
          .orElseThrow(()->new RuntimeException("Product not found with id:"+ productSizeDTO.getProdId()));
      }

      productSize.setSize(productSizeDTO.getSizeProd());
      productSize.setPrice(productSizeDTO.getPrice());
      productSize.setPromoPrice(productSizeDTO.getPricePromo());
      productSize.setUrlImage(productSizeDTO.getImageUrl());
      productSizeRepository.save(productSize);
      log.info("Product size saved event received: {}", productSize.getId());

    });
  }

}
