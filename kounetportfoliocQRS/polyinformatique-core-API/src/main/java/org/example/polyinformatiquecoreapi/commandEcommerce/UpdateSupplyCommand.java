package org.example.polyinformatiquecoreapi.commandEcommerce;

import lombok.Getter;
import org.example.polyinformatiquecoreapi.dtoEcommerce.SupplyDTO;
@Getter
public class UpdateSupplyCommand extends BaseCommand<String> {

  private SupplyDTO supply;

  protected UpdateSupplyCommand(String id, SupplyDTO supply) {
    super(id);
    this.supply = supply;
  }
}
