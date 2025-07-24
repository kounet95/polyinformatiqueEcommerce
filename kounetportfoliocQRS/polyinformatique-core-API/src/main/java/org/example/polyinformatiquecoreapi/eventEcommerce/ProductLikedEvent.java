package org.example.polyinformatiquecoreapi.eventEcommerce;

import lombok.Getter;
import org.example.polyinformatiquecoreapi.dtoEcommerce.LikeDTO;
@Getter
public class ProductLikedEvent {
  private String id;
  private String  user;
  private String product;

  public ProductLikedEvent(String id, String user, String product) {
    this.id = id;
    this.user = user;
    this.product = product;
  }
}
