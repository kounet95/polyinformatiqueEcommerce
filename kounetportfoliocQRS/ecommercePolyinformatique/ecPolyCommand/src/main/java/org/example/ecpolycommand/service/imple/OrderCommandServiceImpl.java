package org.example.ecpolycommand.service.imple;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.example.ecpolycommand.service.OrderCommandService;
import org.example.polyinformatiquecoreapi.commandEcommerce.CreateOrderCommand;
import org.example.polyinformatiquecoreapi.commandEcommerce.DeleteOrderCommand;
import org.example.polyinformatiquecoreapi.commandEcommerce.UpdateOrderCommand;

import org.example.polyinformatiquecoreapi.dtoEcommerce.OrderDTO;
import org.springframework.stereotype.Service;

@Service
public class OrderCommandServiceImpl implements OrderCommandService {

    private final CommandGateway commandGateway;

    public OrderCommandServiceImpl(CommandGateway commandGateway) {

      this.commandGateway = commandGateway;
    }

    @Override
    public void createOrder(OrderDTO dto) {
        commandGateway.sendAndWait(
          new CreateOrderCommand(dto.getId(), dto));
    }

    @Override
    public void updateOrder(String id, OrderDTO dto) {
        commandGateway.sendAndWait(new UpdateOrderCommand(id, dto));
    }

    @Override
    public void deleteOrder(String id) {

      commandGateway.sendAndWait(new DeleteOrderCommand(id));
    }
}
