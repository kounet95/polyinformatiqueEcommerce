package org.example.ecpolyquery.mapper;

import org.example.ecpolyquery.entity.Product;
import org.example.ecpolyquery.entity.ProductSize;

import org.example.polyinformatiquecoreapi.dtoEcommerce.ProductSizeDTO;

import java.util.ArrayList;
import java.util.List;

public class MapperProductSize {

  public static List<ProductSize> mapProductSizes(List<ProductSizeDTO> dtos, Product product) {
    List<ProductSize> sizes = new ArrayList<>();
    if (dtos != null) {
      for (ProductSizeDTO dto : dtos) {
        ProductSize size = ProductSize.builder()
          .id(dto.getId())
          .size(dto.getSizeProd())
          .price(dto.getPrice())
          .promoPrice(dto.getPricePromo())
          .stockAvailable(dto.getStock_available())
          .product(product)
          .build();
        sizes.add(size);
      }
    }
    return sizes;
  }
}
