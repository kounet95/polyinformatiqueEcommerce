package org.example.polyinformatiquecoreapi.eventEcommerce;

import lombok.Getter;
import org.example.polyinformatiquecoreapi.dtoEcommerce.ProductSizeDTO;
import org.example.polyinformatiquecoreapi.event.BaseEvent;

import java.io.Serializable;
@Getter
public class ProductSizeUpdatedEvent extends BaseEvent<String> implements Serializable {
  private ProductSizeDTO productSizeDTO;
  protected ProductSizeUpdatedEvent(String id, ProductSizeDTO productSizeDTO) {
    super(id);
    this.productSizeDTO = productSizeDTO;
  }
}
