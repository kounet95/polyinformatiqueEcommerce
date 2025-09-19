package org.example.ecpolyquery.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.queryhandling.QueryHandler;
import org.example.ecpolyquery.entity.Customer;
import org.example.ecpolyquery.entity.LikeProduct;
import org.example.ecpolyquery.entity.Product;
import org.example.ecpolyquery.entity.ProductSize;
import org.example.ecpolyquery.query.GetLikesByCustomerQuery;
import org.example.ecpolyquery.query.GetLikesByProductQuery;
import org.example.ecpolyquery.query.CountLikesByProductQuery;
import org.example.ecpolyquery.query.CheckCustomerLikedProductQuery;
import org.example.ecpolyquery.repos.CustomerRepository;
import org.example.ecpolyquery.repos.LikeRepository;
import org.example.ecpolyquery.repos.ProductRepository;
import org.example.ecpolyquery.repos.ProductSizeRepository;
import org.example.polyinformatiquecoreapi.dtoEcommerce.LikeDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class LikeQueryHandler {

  private final LikeRepository likeRepository;
  private final CustomerRepository customerRepository;
  private final ProductRepository productRepository;
  private final ProductSizeRepository productSizeRepository;

  /**
   * Récupération tous les likes pour un produit donné
   */
  @QueryHandler
  public List<LikeProduct> handle(GetLikesByProductQuery query) {
    log.debug("Handling GetLikesByProductQuery for productId: {}", query.getProductId());

    ProductSize product = productSizeRepository.findById(query.getProductId())
      .orElseThrow(() -> new RuntimeException("Product not found with id: " + query.getProductId()));

    return likeRepository.findAll().stream()
      .filter(like -> like.getProduct().getId().equals(product.getId()))
      .toList();
  }

  /**
   * Compter le nombre de likes pour un produit donné
   */
  @QueryHandler
  public long handle(CountLikesByProductQuery query) {
    log.debug("Handling CountLikesByProductQuery for productId: {}", query.getProductId());

    ProductSize product = productSizeRepository.findById(query.getProductId())
      .orElseThrow(() -> new RuntimeException("Product not found with id: " + query.getProductId()));

    return likeRepository.countByProduct(product);
  }

  /**
   * Vérifier si un utilisateur a liké un produit
   */
  @QueryHandler
  public boolean handle(CheckCustomerLikedProductQuery query) {
    log.debug("Handling CheckCustomerLikedProductQuery for customerId: {} and productId: {}",
      query.getCustomerId(), query.getProductId());

    return likeRepository.existsByCustomer_IdAndProduct_Id(
      query.getCustomerId(), query.getProductId()
    );
  }

  /**
   * Récupérer tous les likes pour un client donné
   */
  @QueryHandler
  public List<LikeDTO> handle(GetLikesByCustomerQuery query) {
    Customer customer = customerRepository.findById(query.getCustomerId())
      .orElseThrow(() -> new RuntimeException("Customer not found: " + query.getCustomerId()));

    return likeRepository.findAll().stream()
      .filter(like -> like.getCustomer().getId().equals(customer.getId()))
      .map(like -> new LikeDTO(
        like.getId(),
        like.getCustomer().getId(),
        like.getProduct().getId()
      ))
      .toList();
  }


}
