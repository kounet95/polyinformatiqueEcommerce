package org.example.polyinformatiquecoreapi.commandEcommerce;

import lombok.Getter;
import org.example.polyinformatiquecoreapi.dtoEcommerce.OrderStatus;

@Getter
public class UpdateOrderStatusCommand extends BaseCommand<String> {
    private final String barcode;
    private final OrderStatus newStatus;

    public UpdateOrderStatusCommand(String id, String barcode, OrderStatus newStatus) {
        super(id);
        this.barcode = barcode;
        this.newStatus = newStatus;
    }
}
