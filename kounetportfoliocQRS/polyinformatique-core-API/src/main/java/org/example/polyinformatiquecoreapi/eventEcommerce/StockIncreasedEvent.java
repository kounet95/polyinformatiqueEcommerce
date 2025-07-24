package org.example.polyinformatiquecoreapi.eventEcommerce;

import org.example.polyinformatiquecoreapi.dtoEcommerce.StockDTO;
import org.example.polyinformatiquecoreapi.event.BaseEvent;

public class StockIncreasedEvent extends BaseEvent<String> {
    private final StockDTO stockDTO;

    public StockIncreasedEvent(String id, StockDTO stockDTO) {
        super(id);
        this.stockDTO = stockDTO;
    }

    public StockDTO getStockDTO() {
        return stockDTO;
    }
}
