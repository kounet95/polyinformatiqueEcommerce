package org.example.ecpolyquery.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.queryhandling.QueryHandler;
import org.example.polyinformatiquecoreapi.query.WatchEventQuery;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class EventQueryHandler {

    @QueryHandler
    public Object on(WatchEventQuery query) {
        log.debug("Handling WatchEventQuery: {}", query.getId());
        // Implementation depends on what WatchEventQuery is supposed to do
        // and what entity or repository it should interact with

        // This is a placeholder implementation
        return "Event watched with id: " + query.getId();
    }
}
