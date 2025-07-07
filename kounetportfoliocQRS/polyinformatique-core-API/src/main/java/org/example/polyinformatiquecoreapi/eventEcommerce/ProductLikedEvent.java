package org.example.polyinformatiquecoreapi.eventEcommerce;

import lombok.Getter;
import org.example.polyinformatiquecoreapi.dtoEcommerce.LikeDTO;
@Getter
public class ProductLikedEvent {
  private String id;
  private LikeDTO likeDTO;
  public ProductLikedEvent(String id, LikeDTO likeDTO) {
    this.id = id;
    this.likeDTO = likeDTO;
  }
}
