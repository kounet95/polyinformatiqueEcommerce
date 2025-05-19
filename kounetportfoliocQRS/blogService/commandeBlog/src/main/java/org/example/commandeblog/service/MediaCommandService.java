package org.example.commandeblog.service;

import org.example.polyinformatiquecoreapi.dto.MediaDTO;

public interface MediaCommandService {
    void createMedia(MediaDTO dto);
    void updateMedia(String id, MediaDTO dto);
    void deleteMedia(String id);
}