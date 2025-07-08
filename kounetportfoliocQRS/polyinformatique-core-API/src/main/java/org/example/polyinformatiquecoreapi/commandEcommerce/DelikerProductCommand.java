package org.example.polyinformatiquecoreapi.commandEcommerce;

import lombok.Getter;

@Getter
public class DelikerProductCommand extends BaseCommand<String>{
  private String  user;
  private String product;
  public DelikerProductCommand(String id, String user, String product) {
    super(id);
    this.user = user;
    this.product = product;

  }
}
