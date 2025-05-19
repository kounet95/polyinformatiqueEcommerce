package org.example.ecpolyquery.web;

import lombok.AllArgsConstructor;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.example.ecpolyquery.entity.Invoice;
import org.example.ecpolyquery.query.GetAllInvoicesQuery;
import org.example.ecpolyquery.query.GetInvoiceByIdQuery;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/invoices")
@AllArgsConstructor
public class InvoiceController {

    private final QueryGateway queryGateway;

    @GetMapping
    public CompletableFuture<List<Invoice>> getAllInvoices() {
        return queryGateway.query(new GetAllInvoicesQuery(), 
                ResponseTypes.multipleInstancesOf(Invoice.class));
    }

    @GetMapping("/{id}")
    public CompletableFuture<Invoice> getInvoiceById(@PathVariable String id) {
        return queryGateway.query(new GetInvoiceByIdQuery(id), 
                ResponseTypes.instanceOf(Invoice.class));
    }
}