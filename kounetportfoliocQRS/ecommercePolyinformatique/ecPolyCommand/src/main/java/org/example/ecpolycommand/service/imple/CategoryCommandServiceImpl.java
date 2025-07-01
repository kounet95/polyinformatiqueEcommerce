package org.example.ecpolycommand.service.imple;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.example.ecpolycommand.service.CategoryCommandService;
import org.example.polyinformatiquecoreapi.commandEcommerce.CreateCategoryCommand;
import org.example.polyinformatiquecoreapi.commandEcommerce.UpdateCategoryCommand;
import org.example.polyinformatiquecoreapi.commandEcommerce.DeleteCategoryCommand;
import org.example.polyinformatiquecoreapi.dtoEcommerce.CategoryDTO;
import org.springframework.stereotype.Service;

@Service
public class CategoryCommandServiceImpl implements CategoryCommandService {

    private final CommandGateway commandGateway;

    public CategoryCommandServiceImpl(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @Override
    public void createCategory(CategoryDTO dto) {
        commandGateway.sendAndWait(
          new CreateCategoryCommand(dto.getId(), dto));
    }

    @Override
    public void updateCategory(String id, CategoryDTO dto) {
        commandGateway.sendAndWait(new UpdateCategoryCommand(id, dto));
    }

    @Override
    public void deleteCategory(String id) {
        commandGateway.sendAndWait(new DeleteCategoryCommand(id));
    }
}
