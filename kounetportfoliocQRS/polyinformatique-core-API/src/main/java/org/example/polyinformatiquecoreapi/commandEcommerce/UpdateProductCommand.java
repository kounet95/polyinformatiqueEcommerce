package org.example.polyinformatiquecoreapi.commandEcommerce;

import org.example.polyinformatiquecoreapi.dtoEcommerce.ProductDTO;

public class UpdateProductCommand extends BaseCommand<String> {
    private final ProductDTO productDTO;

    public UpdateProductCommand(String id, ProductDTO productDTO) {
        super(id);
        this.productDTO = productDTO;
    }

    public ProductDTO getProductDTO() {
        return productDTO;
    }
}