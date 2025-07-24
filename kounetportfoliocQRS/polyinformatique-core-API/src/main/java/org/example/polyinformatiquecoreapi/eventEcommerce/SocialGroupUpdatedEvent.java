package org.example.polyinformatiquecoreapi.eventEcommerce;

import lombok.Getter;
import org.example.polyinformatiquecoreapi.dtoEcommerce.SocialGroupDTO;
import org.example.polyinformatiquecoreapi.event.BaseEvent;

/**
 * Event emitted when a social group is created
 */
@Getter
public class SocialGroupUpdatedEvent extends BaseEvent<String> {

    private final SocialGroupDTO socialGroupDTO;

    public SocialGroupUpdatedEvent(String id, SocialGroupDTO socialGroupDTO) {
      super(id);
        this.socialGroupDTO = socialGroupDTO;
    }
}
