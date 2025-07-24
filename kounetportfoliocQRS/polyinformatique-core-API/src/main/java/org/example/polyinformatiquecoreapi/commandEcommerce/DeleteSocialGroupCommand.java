package org.example.polyinformatiquecoreapi.commandEcommerce;

import lombok.Getter;
import org.example.polyinformatiquecoreapi.event.BaseEvent;

@Getter
public class DeleteSocialGroupCommand extends BaseEvent<String> {


  public DeleteSocialGroupCommand(String id) {
    super(id);
  }
}
