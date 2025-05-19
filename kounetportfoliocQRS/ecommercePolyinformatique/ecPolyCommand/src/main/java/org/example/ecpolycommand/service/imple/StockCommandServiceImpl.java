package org.example.ecpolycommand.service.imple;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.example.ecpolycommand.service.StockCommandService;
import org.example.polyinformatiquecoreapi.commandEcommerce.AddStockCommand;
import org.example.polyinformatiquecoreapi.dtoEcommerce.StockDTO;
import org.springframework.stereotype.Service;

@Service
public class StockCommandServiceImpl implements StockCommandService {

    private final CommandGateway commandGateway;

    public StockCommandServiceImpl(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @Override
    public void addStock(StockDTO dto) {
        commandGateway.sendAndWait(new AddStockCommand(dto.getId(), dto));
    }
}