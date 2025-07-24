package org.example.queryblog.mapper;

import org.example.polyinformatiquecoreapi.dto.TagDTO;

import org.example.queryblog.entite.Tag;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TagMapper {
    TagDTO toDTO(Tag tag);
}
