package org.example.polyinformatiquecoreapi.commandEcommerce;

import org.example.polyinformatiquecoreapi.dtoEcommerce.StockDTO;

public class AddStockCommand extends BaseCommand<String> {
    private final StockDTO stockDTO;

    public AddStockCommand(String id, StockDTO stockDTO) {
        super(id);
        this.stockDTO = stockDTO;
    }

    public StockDTO getStockDTO() {
        return stockDTO;
    }
}
