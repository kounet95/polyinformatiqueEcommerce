package org.example.polyinformatiquecoreapi.eventEcommerce;

import lombok.Getter;
import org.example.polyinformatiquecoreapi.dtoEcommerce.SocialGroupDTO;
import org.example.polyinformatiquecoreapi.event.BaseEvent;

/**
 * Event emitted when a social group is created
 */
@Getter
public class SocialGroupDeletedEvent extends BaseEvent<String> {

  protected SocialGroupDeletedEvent(String id) {
    super(id);
  }
}
