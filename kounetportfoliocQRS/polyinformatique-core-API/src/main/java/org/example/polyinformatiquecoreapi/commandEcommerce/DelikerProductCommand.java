package org.example.polyinformatiquecoreapi.commandEcommerce;

import lombok.Getter;

@Getter
public class DelikerProductCommand extends BaseCommand<String>{

  protected DelikerProductCommand(String id) {
    super(id);
  }
}
