package org.example.ecpolyquery.web;

import lombok.RequiredArgsConstructor;
import org.axonframework.queryhandling.QueryGateway;
import org.example.ecpolyquery.entity.LikeProduct;
import org.example.ecpolyquery.query.CountLikesByProductQuery;
import org.example.ecpolyquery.query.GetLikesByProductQuery;
import org.example.ecpolyquery.query.CheckCustomerLikedProductQuery;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

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
    return queryGateway.query(query, org.axonframework.messaging.responsetypes.ResponseTypes.multipleInstancesOf(LikeProduct.class));
  }

  /**
   * Vérifier si un utilisateur a liké un produit.
   */
  @GetMapping("/{productId}/likes/exists")
  public CompletableFuture<ResponseEntity<Boolean>> checkIfCustomerLiked(
    @PathVariable String productId,
    @RequestParam String customerId) {
    CheckCustomerLikedProductQuery query = new CheckCustomerLikedProductQuery(customerId, productId);
    return queryGateway.query(query, Boolean.class)
      .thenApply(ResponseEntity::ok);
  }
}
