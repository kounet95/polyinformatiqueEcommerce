package org.example.polyinformatiquecoreapi.commandEcommerce;


import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DeleteOrderLineCommand {
  private String id;
  public DeleteOrderLineCommand(String id) {
   this.id=id;
  }
}
