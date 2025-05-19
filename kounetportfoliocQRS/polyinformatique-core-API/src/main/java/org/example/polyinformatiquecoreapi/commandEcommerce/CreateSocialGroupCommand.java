package org.example.polyinformatiquecoreapi.commandEcommerce;

import org.example.polyinformatiquecoreapi.dtoEcommerce.SocialGroupDTO;

public class CreateSocialGroupCommand extends BaseCommand<String> {
    private final SocialGroupDTO socialGroupDTO;

    public CreateSocialGroupCommand(String id, SocialGroupDTO socialGroupDTO) {
        super(id);
        this.socialGroupDTO = socialGroupDTO;
    }

    public SocialGroupDTO getSocialGroupDTO() {
        return socialGroupDTO;
    }
}