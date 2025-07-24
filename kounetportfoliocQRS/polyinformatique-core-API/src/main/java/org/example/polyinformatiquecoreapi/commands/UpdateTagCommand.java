package org.example.polyinformatiquecoreapi.commands;

import org.example.polyinformatiquecoreapi.dto.TagDTO;

public class UpdateTagCommand  extends BaseCommand<String>{
    private TagDTO dto;
    public UpdateTagCommand(String id, TagDTO dto) {
        super(id);
        this.dto = dto;

    }
    public TagDTO getDto() {
        return dto;
    }
}
