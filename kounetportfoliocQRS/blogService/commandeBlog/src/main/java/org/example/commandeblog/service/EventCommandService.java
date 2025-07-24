package org.example.commandeblog.service;

import org.example.polyinformatiquecoreapi.dto.EventDTO;

public interface EventCommandService {
    void createEvent(EventDTO dto);
    void updateEvent(String id, EventDTO dto);
    void deleteEvent(String id);
}