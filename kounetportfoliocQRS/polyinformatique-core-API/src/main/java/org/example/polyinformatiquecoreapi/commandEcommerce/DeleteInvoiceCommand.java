package org.example.polyinformatiquecoreapi.commandEcommerce;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DeleteInvoiceCommand extends BaseCommand<String>{

  public DeleteInvoiceCommand(String id) {
    super(id);
  }
}
