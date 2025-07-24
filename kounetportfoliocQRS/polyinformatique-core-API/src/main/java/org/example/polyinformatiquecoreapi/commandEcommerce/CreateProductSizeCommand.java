package org.example.polyinformatiquecoreapi.commandEcommerce;

import org.example.polyinformatiquecoreapi.dtoEcommerce.ProductSizeDTO;

public class CreateProductSizeCommand  extends BaseCommand<String> {

    private ProductSizeDTO productSizeDTO;

    public CreateProductSizeCommand(String id, ProductSizeDTO productSizeDTO) {
        super(id);
        this.productSizeDTO = productSizeDTO;
    }
    public ProductSizeDTO getProductSizeDTO() {
        return productSizeDTO;
    }
}
