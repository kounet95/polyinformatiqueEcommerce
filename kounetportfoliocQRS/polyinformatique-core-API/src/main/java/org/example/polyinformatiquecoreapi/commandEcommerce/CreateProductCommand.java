package org.example.polyinformatiquecoreapi.commandEcommerce;

import org.example.polyinformatiquecoreapi.dtoEcommerce.ProductDTO;

public class CreateProductCommand  extends BaseCommand<String>{
    private ProductDTO productDTO;

    public CreateProductCommand(String id, ProductDTO productDTO) {
        super(id);
        this.productDTO = productDTO;
    }
    public ProductDTO getProductDTO() {
        return productDTO;
    }
}
