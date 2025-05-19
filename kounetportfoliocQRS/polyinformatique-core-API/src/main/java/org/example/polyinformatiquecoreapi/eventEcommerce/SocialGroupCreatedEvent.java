package org.example.polyinformatiquecoreapi.eventEcommerce;

import lombok.Getter;
import org.example.polyinformatiquecoreapi.dtoEcommerce.SocialGroupDTO;

/**
 * Event emitted when a social group is created
 */
@Getter
public class SocialGroupCreatedEvent {
    private final String id;
    private final SocialGroupDTO socialGroupDTO;

    public SocialGroupCreatedEvent(String id, SocialGroupDTO socialGroupDTO) {
        this.id = id;
        this.socialGroupDTO = socialGroupDTO;
    }
}