package org.example.queryblog.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.axonframework.queryhandling.QueryUpdateEmitter;
import org.example.polyinformatiquecoreapi.dto.DomainDTO;
import org.example.polyinformatiquecoreapi.event.DomainCreateEvent;
import org.example.queryblog.entite.Domain;
import org.example.queryblog.mapper.DomainMapper;
import org.example.queryblog.query.GetAllDomainQuery;
import org.example.queryblog.query.GetDomainByIdQuery;
import org.example.queryblog.repos.DomainRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class DomainQueryHandler {

    private final DomainRepository domainRepository;
    private final DomainMapper domainMapper;
    private final QueryUpdateEmitter queryUpdateEmitter;

    @QueryHandler
    public List<DomainDTO> on(GetAllDomainQuery query) {
        List<Domain> domains = domainRepository.findAll();
        return domains.stream()
                .map(domainMapper::toDTO)
                .collect(Collectors.toList());
    }

    @QueryHandler
    public DomainDTO on(GetDomainByIdQuery query) {
        Optional<Domain> optionalDomain = domainRepository.findById(query.getId());
        return optionalDomain
                .map(domainMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Domain not found with id: " + query.getId()));
    }

    @EventHandler
    public void on(DomainCreateEvent event) {
        log.debug("Handling DomainCreateEvent for subscription queries: {}", event.getId());
        // The event already contains the DomainDTO
        DomainDTO domainDTO = event.getDomainDTO();

        // If there were a WatchDomainQuery, we would emit updates here
        // queryUpdateEmitter.emit(WatchDomainQuery.class,
        //        query -> query.getDomainId().equals(event.getId()),
        //        domainDTO);
    }
}
