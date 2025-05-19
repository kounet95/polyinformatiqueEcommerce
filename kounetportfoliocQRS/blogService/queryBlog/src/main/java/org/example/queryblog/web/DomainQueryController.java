package org.example.queryblog.web;

import lombok.RequiredArgsConstructor;
import org.axonframework.queryhandling.QueryGateway;
import org.example.polyinformatiquecoreapi.dto.DomainDTO;
import org.example.queryblog.query.GetAllDomainQuery;
import org.example.queryblog.query.GetDomainByIdQuery;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/domains")
@RequiredArgsConstructor
public class DomainQueryController {

    private final QueryGateway queryGateway;

    @GetMapping
    public CompletableFuture<List<DomainDTO>> getAllDomains() {
        return queryGateway.query(new GetAllDomainQuery(),
                org.axonframework.messaging.responsetypes.ResponseTypes.multipleInstancesOf(DomainDTO.class));
    }

    @GetMapping("/{id}")
    public CompletableFuture<DomainDTO> getDomainById(@PathVariable String id) {
        return queryGateway.query(new GetDomainByIdQuery(id),
                org.axonframework.messaging.responsetypes.ResponseTypes.instanceOf(DomainDTO.class));
    }
}
