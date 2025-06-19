package org.example.polyinformatiquecoreapi.commandEcommerce;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DeleteProductSizeCommand {
  private String id;
  public DeleteProductSizeCommand(String id) {
    this.id = id;
  }
}
