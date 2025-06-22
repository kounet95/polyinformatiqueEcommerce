package org.example.ecpolyquery.service;

import org.example.ecpolyquery.entity.Product;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class ProductSpecification {

  public static Specification<Product> withFilters(
    String categoryId,
    String couleurs,
    String searchKeyword

  ) {
    return (root, query, cb) -> {
      List<Predicate> predicates = new ArrayList<>();
      if (categoryId != null) {
        predicates.add(cb.equal(
          root.get("category").get("id"), categoryId));
      }
      if (couleurs != null) {
        predicates.add(cb.equal(root.get("couleur"), couleurs));
      }
      if (searchKeyword != null) {
        predicates.add(cb.like(cb.lower(root.get("name")), "%" + searchKeyword.toLowerCase() + "%"));
      }
      // Ajoute d'autres critères ici
      return cb.and(predicates.toArray(new Predicate[0]));
    };
  }
}
