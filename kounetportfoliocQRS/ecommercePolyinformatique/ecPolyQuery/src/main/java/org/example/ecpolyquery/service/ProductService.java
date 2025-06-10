package org.example.ecpolyquery.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.example.ecpolyquery.entity.Product;
import org.example.ecpolyquery.entity.ProductSize;
import org.example.ecpolyquery.entity.SocialGroup;
import org.example.ecpolyquery.entity.Subcategory;
import org.example.ecpolyquery.repos.ProductRepository;
import org.example.ecpolyquery.repos.SocialGroupRepository;
import org.example.ecpolyquery.repos.SubcategoryRepository;
import org.example.polyinformatiquecoreapi.dtoEcommerce.ProductDTO;
import org.example.polyinformatiquecoreapi.dtoEcommerce.ProductSizeDTO;
import org.example.polyinformatiquecoreapi.eventEcommerce.ProductCreatedEvent;
import org.example.polyinformatiquecoreapi.eventEcommerce.ProductDeletedEvent;
import org.example.polyinformatiquecoreapi.eventEcommerce.ProductUpdatedEvent;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
@AllArgsConstructor
public class ProductService {

  private final ProductRepository productRepository;
  private final SubcategoryRepository subcategoryRepository;
  private final SocialGroupRepository socialGroupRepository;

  private ProductSize mapProductSize(ProductSizeDTO dto) {
    return dto != null ? ProductSize.valueOf(dto.name()) : null;
  }

  @EventHandler
  public void on(ProductCreatedEvent event) {
    log.debug("Handling ProductCreatedEvent: {}", event.getId());
    ProductDTO productDTO = event.getProductDTO();

    Subcategory subcategory = null;
    if (productDTO.getSubcategoryId() != null && !productDTO.getSubcategoryId().isEmpty()) {
      subcategory = subcategoryRepository.findById(productDTO.getSubcategoryId())
        .orElseThrow(() -> new RuntimeException("Subcategory not found with id: " + productDTO.getSubcategoryId()));
    }

    SocialGroup socialGroup = null;
    if (productDTO.getSocialGroupId() != null && !productDTO.getSocialGroupId().isEmpty()) {
      socialGroup = socialGroupRepository.findById(productDTO.getSocialGroupId())
        .orElseThrow(() -> new RuntimeException("SocialGroup not found with id: " + productDTO.getSocialGroupId()));
    }

    LocalDateTime createdAt = productDTO.getCreatedAt() != null ? productDTO.getCreatedAt() : LocalDateTime.now();

    Product product = Product.builder()
      .id(event.getId())
      .name(productDTO.getName())
      .description(productDTO.getDescription())
      .createdAt(createdAt)
      .isActive(productDTO.getActive())
      .subcategory(subcategory)
      .socialGroup(socialGroup)
      .sizes(mapProductSize(productDTO.getProductSize()))
      .build();

    productRepository.save(product);
    log.info("Product created with ID: {}", product.getId());
  }

  @EventHandler
  public void on(ProductDeletedEvent event) {
    log.debug("Handling ProductDeletedEvent: {}", event.getId());

    productRepository.findById(event.getId())
      .ifPresent(product -> {
        productRepository.delete(product);
        log.info("Product deleted with ID: {}", event.getId());
      });
  }

  @EventHandler
  public void on(ProductUpdatedEvent event) {
    log.debug("Handling ProductUpdatedEvent: {}", event.getId());
    ProductDTO productDTO = event.getProductDTO();

    productRepository.findById(event.getId())
      .ifPresent(product -> {
        // Update subcategory if provided
        if (productDTO.getSubcategoryId() != null && !productDTO.getSubcategoryId().isEmpty()) {
          Subcategory subcategory = subcategoryRepository.findById(productDTO.getSubcategoryId())
            .orElseThrow(() -> new RuntimeException("Subcategory not found with id: " + productDTO.getSubcategoryId()));
          product.setSubcategory(subcategory);
        }

        // Update social group if provided
        if (productDTO.getSocialGroupId() != null && !productDTO.getSocialGroupId().isEmpty()) {
          SocialGroup socialGroup = socialGroupRepository.findById(productDTO.getSocialGroupId())
            .orElseThrow(() -> new RuntimeException("SocialGroup not found with id: " + productDTO.getSocialGroupId()));
          product.setSocialGroup(socialGroup);
        }



        // Update other fields
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setActive(productDTO.getActive());
        product.setSizes(mapProductSize(productDTO.getProductSize()));

        productRepository.save(product);
        log.info("Product updated with ID: {}", product.getId());
      });
  }
}
