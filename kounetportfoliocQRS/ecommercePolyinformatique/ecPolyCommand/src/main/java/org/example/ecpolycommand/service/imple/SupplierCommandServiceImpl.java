package org.example.ecpolycommand.service.imple;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.example.ecpolycommand.service.SupplierCommandService;
import org.example.polyinformatiquecoreapi.commandEcommerce.CreateSupplierCommand;
import org.example.polyinformatiquecoreapi.commandEcommerce.DeleteSupplierCommand;
import org.example.polyinformatiquecoreapi.dtoEcommerce.SupplierDTO;
import org.springframework.stereotype.Service;

@Service
public class SupplierCommandServiceImpl implements SupplierCommandService {

    private final CommandGateway commandGateway;

    public SupplierCommandServiceImpl(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @Override
    public void createSupplier(SupplierDTO dto) {
        // Envoie la commande de création de fournisseur
        commandGateway.sendAndWait(new CreateSupplierCommand(dto.getId(), dto));
    }

    @Override
    public void deleteSupplier(String id) {
        // Envoie la commande de suppression de fournisseur
        commandGateway.sendAndWait(new DeleteSupplierCommand(id));
    }
}