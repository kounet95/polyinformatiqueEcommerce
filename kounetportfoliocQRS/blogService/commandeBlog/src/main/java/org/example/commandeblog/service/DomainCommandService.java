package org.example.commandeblog.service;

import org.example.polyinformatiquecoreapi.dto.DomainDTO;

public interface DomainCommandService {
    void createDomain(DomainDTO dto);
    void updateDomain(String id, DomainDTO dto);
    void deleteDomain(String id);
}