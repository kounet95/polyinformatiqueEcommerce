package org.example.polyinformatiquecoreapi.commandEcommerce;

import lombok.Getter;
import org.example.polyinformatiquecoreapi.dtoEcommerce.CustomerEcommerceDTO;
@Getter
public class CustomerUpdatedCommand extends BaseCommand<String>{

  private CustomerEcommerceDTO customerEcommerceDTO;

  public CustomerUpdatedCommand(String id, CustomerEcommerceDTO customerEcommerceDTO) {
    super(id);
    this.customerEcommerceDTO = customerEcommerceDTO;
  }
}
