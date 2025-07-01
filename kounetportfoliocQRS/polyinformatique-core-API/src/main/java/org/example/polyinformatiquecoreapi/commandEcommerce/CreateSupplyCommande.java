package org.example.polyinformatiquecoreapi.commandEcommerce;

import lombok.Getter;
import org.example.polyinformatiquecoreapi.dtoEcommerce.SupplyDTO;
@Getter
public class CreateSupplyCommande extends BaseCommand<String>{

  private SupplyDTO supplyDTO;

  public CreateSupplyCommande(String id, SupplyDTO supplyDTO) {
    super(id);
    this.supplyDTO = supplyDTO;
  }
}
