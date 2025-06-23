package org.example.ecpolyquery.service;

import org.example.ecpolyquery.entity.Product;
import org.example.ecpolyquery.entity.ProductSize;
import org.example.polyinformatiquecoreapi.dtoEcommerce.SizeProd;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate; // <-- bon import !

import java.util.ArrayList;
import java.util.List;

public class ProductSpecification {
  public static Specification<Product> withFilters(
    String categoryId,
    String socialGroupId,
    String sousCategories,
    String searchKeyword,
    String getMotifs
  ) {
    return (root, query, cb) -> {
      List<Predicate> predicates = new ArrayList<>();
      if (categoryId != null) predicates.add(cb.equal(root.get("category").get("id"), categoryId));
      if (socialGroupId != null) predicates.add(cb.equal(root.get("socialGroup").get("id"), socialGroupId));
      if (sousCategories != null) predicates.add(cb.equal(root.get("sousCategorie"), sousCategories));
      if (searchKeyword != null) predicates.add(cb.like(cb.lower(root.get("name")), "%" + searchKeyword.toLowerCase() + "%"));
      return cb.and(predicates.toArray(new Predicate[0]));
    };
  }
  public static Specification<ProductSize> withFiltersSize(
    Integer selectedPrice,
    Integer selectedPricePromo,
    SizeProd prodsize
  ) {
    return (root, query, cb) -> {
      List<Predicate> predicates = new ArrayList<>();
      if (selectedPrice != null && selectedPrice > 0)
        predicates.add(cb.equal(root.get("price"), selectedPrice));
      if (selectedPricePromo != null && selectedPricePromo > 0)
        predicates.add(cb.equal(root.get("promoPrice"), selectedPricePromo));
      if (prodsize != null)
        predicates.add(cb.equal(root.get("size"), prodsize));
      return cb.and(predicates.toArray(new Predicate[0]));
    };
  }
}
