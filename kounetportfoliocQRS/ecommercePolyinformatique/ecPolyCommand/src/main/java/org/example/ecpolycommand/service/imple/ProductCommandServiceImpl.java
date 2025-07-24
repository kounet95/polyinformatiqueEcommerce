package org.example.ecpolycommand.service.imple;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.example.ecpolycommand.service.ProductCommandService;
import org.example.polyinformatiquecoreapi.commandEcommerce.CreateProductCommand;
import org.example.polyinformatiquecoreapi.commandEcommerce.UpdateProductCommand;
import org.example.polyinformatiquecoreapi.commandEcommerce.DeleteProductCommand;
import org.example.polyinformatiquecoreapi.dtoEcommerce.ProductDTO;
import org.springframework.stereotype.Service;

@Service
public class ProductCommandServiceImpl implements ProductCommandService {

    private final CommandGateway commandGateway;

    public ProductCommandServiceImpl(CommandGateway commandGateway) {

      this.commandGateway = commandGateway;
    }

    @Override
    public void createProduct(ProductDTO dto) {
        commandGateway.sendAndWait(
          new CreateProductCommand(dto.getId(), dto));
    }

    @Override
    public void updateProduct(String id, ProductDTO dto) {
        commandGateway.sendAndWait(
          new UpdateProductCommand(id, dto));
    }

    @Override
    public void deleteProduct(String id) {

      commandGateway.sendAndWait(new DeleteProductCommand(id));
    }
}
