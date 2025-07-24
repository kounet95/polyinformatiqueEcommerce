package org.example.polyinformatiquecoreapi.commandEcommerce;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DeleteSubcategoryCommand {
  private String subcategoryId;
  public DeleteSubcategoryCommand(String subcategoryId) {}
}
