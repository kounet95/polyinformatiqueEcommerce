package org.example.ecpolycommand.service;

import org.example.polyinformatiquecoreapi.dtoEcommerce.OrderDTO;

public interface OrderCommandService {
    void createOrder(OrderDTO dto);
    void updateOrder(String id, OrderDTO dto);
    void deleteOrder(String id);
}