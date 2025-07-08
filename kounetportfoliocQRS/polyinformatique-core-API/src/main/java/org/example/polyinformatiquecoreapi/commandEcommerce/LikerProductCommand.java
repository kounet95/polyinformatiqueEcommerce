package org.example.polyinformatiquecoreapi.commandEcommerce;

import lombok.Getter;
import org.example.polyinformatiquecoreapi.dtoEcommerce.LikeDTO;
@Getter
public class LikerProductCommand extends BaseCommand<String>{

  private String  user;
  private String product;

  public LikerProductCommand(String id, String user, String product) {
    super(id);
    this.user = user;
    this.product = product;
  }


}
