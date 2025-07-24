package org.example.polyinformatiquecoreapi.commands;

import org.example.polyinformatiquecoreapi.dto.MediaDTO;

public class UpdateMediaCommand extends BaseCommand<String> {

    private MediaDTO mediaDTO;

    public UpdateMediaCommand(String id, MediaDTO mediaDTO) {
        super(id);
        this.mediaDTO = mediaDTO;
    }

    public MediaDTO getMediaDTO() {
        return mediaDTO;
    }
}
