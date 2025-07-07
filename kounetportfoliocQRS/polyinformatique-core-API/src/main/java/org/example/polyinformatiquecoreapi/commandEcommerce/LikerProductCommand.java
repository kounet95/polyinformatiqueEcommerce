package org.example.polyinformatiquecoreapi.commandEcommerce;

import lombok.Getter;
import org.example.polyinformatiquecoreapi.dtoEcommerce.LikeDTO;
@Getter
public class LikerProductCommand extends BaseCommand<String>{


  private LikeDTO likeDTO;

  protected LikerProductCommand(String id, LikeDTO likeDTO) {
    super(id);
    this.likeDTO = likeDTO;
  }
}
