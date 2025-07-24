package org.example.polyinformatiquecoreapi.eventEcommerce;

import lombok.Getter;
import lombok.Setter;
import org.example.polyinformatiquecoreapi.dtoEcommerce.ProductSizeDTO;
@Getter @Setter
public class ProductSizeCreatedEvent {
  public String id;
  private ProductSizeDTO productSizeDTO;
  public ProductSizeCreatedEvent(String id, ProductSizeDTO productSizeDTO) {
    this.id = id;
    this.productSizeDTO = productSizeDTO;
  }

}
