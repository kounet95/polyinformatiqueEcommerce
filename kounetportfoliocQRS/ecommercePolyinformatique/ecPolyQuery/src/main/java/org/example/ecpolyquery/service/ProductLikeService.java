package org.example.ecpolyquery.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.example.ecpolyquery.entity.Customer;
import org.example.ecpolyquery.entity.LikeProduct;
import org.example.ecpolyquery.entity.Product;
import org.example.ecpolyquery.entity.ProductSize;
import org.example.ecpolyquery.repos.CustomerRepository;
import org.example.ecpolyquery.repos.LikeRepository;
import org.example.ecpolyquery.repos.ProductRepository;
import org.example.ecpolyquery.repos.ProductSizeRepository;
import org.example.polyinformatiquecoreapi.eventEcommerce.ProductLikedEvent;
import org.example.polyinformatiquecoreapi.eventEcommerce.ProductdeLikedEvent;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductLikeService {

  private final LikeRepository likeRepository;
  private final CustomerRepository customerRepository;
  private final ProductRepository productRepository;
  private final ProductSizeRepository productSizeRepository;

  @EventHandler
  public void on(ProductLikedEvent event) {
    log.debug("Handling ProductLikedEvent: {}", event.getId());

    Customer customer = customerRepository.findById(event.getUser())
      .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + event.getUser()));

    ProductSize product = productSizeRepository.findById(event.getProduct())
      .orElseThrow(() -> new RuntimeException("Product not found with ID: " + event.getProduct()));

    boolean alreadyLiked = likeRepository.existsByCustomerAndProduct(customer, product);
    if (alreadyLiked) {
      log.info("User {} already liked product {} — skipping.", event.getUser(), event.getProduct());
      return;
    }

    LikeProduct like = LikeProduct.builder()
      .id(event.getId())
      .customer(customer)
      .product(product)
      .build();

    likeRepository.save(like);

    log.info("Like saved with ID: {}", like.getId());
  }

  @EventHandler
  public void on(ProductdeLikedEvent event) {
    log.debug("Handling ProductdeLikedEvent: {}", event.getId());

    likeRepository.findById(event.getId()).ifPresent(like -> {
      likeRepository.delete(like);
      log.info("Like deleted with ID: {}", event.getId());
    });
  }

}
