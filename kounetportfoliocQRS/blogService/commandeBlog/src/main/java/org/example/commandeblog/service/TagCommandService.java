package org.example.commandeblog.service;

import org.example.polyinformatiquecoreapi.dto.TagDTO;

public interface TagCommandService {
    void createTag(TagDTO dto);
    void updateTag(String id, TagDTO dto);
    void deleteTag(String id);
}