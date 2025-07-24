package org.example.queryblog.mapper;

import org.example.polyinformatiquecoreapi.dto.EventDTO;
import org.example.queryblog.entite.Event;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EventMapper {
    EventDTO toDTO(Event event);
}