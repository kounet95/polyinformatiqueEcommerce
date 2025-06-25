package org.example.polyinformatiquecoreapi.dtoEcommerce;

import lombok.Getter;
import org.example.polyinformatiquecoreapi.commandEcommerce.BaseCommand;

@Getter
public class CreatedAddressEvent extends BaseCommand<String> {

  private AddressDTO addressDTO;

  public CreatedAddressEvent(String id, AddressDTO addressDTO) {
    super(id);
    this.addressDTO = addressDTO;
  }
}
