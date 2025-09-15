package org.example.ecpolyquery.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.example.ecpolyquery.entity.Customer;
import org.example.ecpolyquery.entity.LikeProduct;
import org.example.ecpolyquery.entity.ProductSize;
import org.example.ecpolyquery.repos.CustomerRepository;
import org.example.ecpolyquery.repos.LikeRepository;
import org.example.ecpolyquery.repos.ProductRepository;
import org.example.ecpolyquery.repos.ProductSizeRepository;
import org.example.polyinformatiquecoreapi.eventEcommerce.ProductLikedEvent;

import org.example.polyinformatiquecoreapi.eventEcommerce.ProductUnlikedEvent;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class LikeService {

  private final LikeRepository likeRepository;

  private final ProductSizeRepository productSizeRepository;
  private final CustomerRepository customerRepository;

  @EventHandler
  public void on(ProductLikedEvent event) {
    log.debug("Handling ProductLikedEvent: {}", event.getProduct(), event.getUser());

    // Récupère l'entité Customer et ProductSize à partir de l'event
    Optional<Customer> customerOpt = customerRepository.findById(event.getUser());
    Optional<ProductSize> productOpt = productSizeRepository.findById(event.getProduct());

    if (customerOpt.isEmpty()) {
      log.warn("Customer not found with id: {}", event.getUser());
      return;
    }
    if (productOpt.isEmpty()) {
      log.warn("ProductSize not found with id: {}", event.getProduct());
      return;
    }

    LikeProduct like = LikeProduct.builder()
      .id(event.getId())
      .customer(customerOpt.get())
      .product(productOpt.get())
      .createdAt(LocalDateTime.now())
      .build();

    likeRepository.save(like);
    log.info("LikeProduct saved: {} for customer {} and productSize {}", like.getId(), event.getProduct(), event.getUser());
  }

  @EventHandler
  public void on(ProductUnlikedEvent event) {
    log.debug("Handling ProductUnlikedEvent: {}", event.getProductId());
    if (likeRepository.existsById(event.getProductId())) {
      likeRepository.deleteById(event.getProductId());
      log.info("LikeProduct deleted: {}", event.getProductId());
    } else {
      log.warn("Tried to delete non-existent LikeProduct: {}", event.getProductId());
    }
  }
}
