package org.example.ecpolyquery.web;

import lombok.RequiredArgsConstructor;
import org.axonframework.messaging.responsetypes.ResponseType;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.example.ecpolyquery.entity.LikeProduct;
import org.example.ecpolyquery.entity.ProductSize;
import org.example.ecpolyquery.query.CountLikesByProductQuery;
import org.example.ecpolyquery.query.GetLikesByCustomerQuery;
import org.example.ecpolyquery.query.GetLikesByProductQuery;
import org.example.ecpolyquery.query.CheckCustomerLikedProductQuery;

import org.example.polyinformatiquecoreapi.dtoEcommerce.LikeDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/like")
@RequiredArgsConstructor
public class LikeController {

  private final QueryGateway queryGateway;

  /**
   * Récupérer le nombre total de likes pour un produit donné.
   */
  @GetMapping("/{productId}/likes/count")
  public CompletableFuture<Long> getLikesCount(@PathVariable String productId) {
    CountLikesByProductQuery query = new CountLikesByProductQuery(productId);
    return queryGateway.query(query, Long.class);
  }

  /**
   * Récupérer la liste des Likes pour un produit.
   */
  @GetMapping("/{productId}/likes")
  public CompletableFuture<List<LikeProduct>> getLikesByProduct(@PathVariable String productId) {
    GetLikesByProductQuery query = new GetLikesByProductQuery(productId);
    return queryGateway.query(query,
      org.axonframework.messaging.responsetypes.ResponseTypes.multipleInstancesOf(LikeProduct.class));
  }

  /**
   * Vérifier si un utilisateur a liké un produit.
   */
  @GetMapping("/{productSizeId}/likes/exists")
  public CompletableFuture<ResponseEntity<Boolean>> checkIfCustomerLiked(
    @PathVariable("productSizeId") String productSizeId,
    @RequestParam String customerId) {

    CheckCustomerLikedProductQuery query =
      new CheckCustomerLikedProductQuery(customerId, productSizeId);

    return queryGateway.query(query, Boolean.class)
      .thenApply(ResponseEntity::ok);
  }

  @GetMapping("/customer/{customerId}/likes")
  public CompletableFuture<List<LikeDTO>> getLikesByCustomer(@PathVariable String customerId) {
    return queryGateway.query(
      new GetLikesByCustomerQuery(customerId),
      ResponseTypes.multipleInstancesOf(LikeDTO.class)
    );
  }


  // je vais utilise le Mapping statique dans le contrôleur (ou LikeMapper séparé)
  public static LikeDTO toDto(LikeProduct like) {
    return LikeDTO.builder()
      .id(like.getId())
      .user(like.getCustomer() != null ? like.getCustomer().getId() : null)
      .product(like.getProduct() != null ? like.getProduct().getId() : null)
      .build();
  }

}
