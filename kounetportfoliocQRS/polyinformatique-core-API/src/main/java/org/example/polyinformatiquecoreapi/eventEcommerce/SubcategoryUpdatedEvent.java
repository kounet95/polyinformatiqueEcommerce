package org.example.polyinformatiquecoreapi.eventEcommerce;

import org.example.polyinformatiquecoreapi.dtoEcommerce.SubcategoryDTO;
import org.example.polyinformatiquecoreapi.event.BaseEvent;

public class SubcategoryUpdatedEvent extends BaseEvent<String> {
  private SubcategoryDTO subcategoryDTO;

  public SubcategoryUpdatedEvent(String id, SubcategoryDTO subcategoryDTO) {
    super(id);
    this.subcategoryDTO = subcategoryDTO;
  }

  public SubcategoryDTO getSubcategoryDTO() {
    return subcategoryDTO;
  }
}
