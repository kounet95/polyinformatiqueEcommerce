package org.example.polyinformatiquecoreapi.eventEcommerce;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.example.polyinformatiquecoreapi.dtoEcommerce.StockDTO;
import org.example.polyinformatiquecoreapi.event.BaseEvent;
@Getter
public class StockDecreasedEvent extends BaseEvent<String> {
    private double quantity;

    public StockDecreasedEvent(String id, double quantity) {
        super(id);
        this.quantity = quantity;
    }

    public double getStockDTO() {
        return quantity;
    }
}
