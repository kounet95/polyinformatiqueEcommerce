package org.example.ecpolyquery.service;

import lombok.RequiredArgsConstructor;
import org.example.ecpolyquery.entity.Customer;
import org.example.ecpolyquery.entity.Like;
import org.example.ecpolyquery.entity.Product;
import org.example.ecpolyquery.repos.LikeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ProductLikeService {

  private final LikeRepository likeRepository;

  public boolean toggleLike(Customer customer, Product product) {
    boolean exists = likeRepository.existsByCustomerAndProduct(customer, product);

    if (exists) {
      likeRepository.deleteByCustomerAndProduct(customer, product);
      return false; // like retiré
    } else {
      Like like = Like.builder()
        .customer(customer)
        .product(product)
        .createdAt(LocalDateTime.now())
        .build();
      likeRepository.save(like);
      return true; // like ajouté
    }
  }

  public long countLikes(Product product) {
    return likeRepository.countByProduct(product);
  }
}

