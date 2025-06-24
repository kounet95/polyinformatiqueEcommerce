package org.example.ecpolyquery.service;

import jakarta.persistence.criteria.JoinType;
import org.example.ecpolyquery.entity.Product;
import org.example.ecpolyquery.entity.ProductSize;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class ProductSpecification {

  public static Specification<ProductSize> hasProductsizeName(String productName) {
    return (root, query, cb) ->
      productName == null ? null :
        cb.like(
          cb.lower(root.join("productId").get("name")),
          "%" + productName.toLowerCase() + "%"
        );
  }

  public static Specification<ProductSize> hasSubcategoryId(String subcategoryId) {
    return (root, query, cb) ->
      subcategoryId == null ? null :
        cb.equal(root.join("productId").join("subcategory").get("id"), subcategoryId);
  }

  public static Specification<ProductSize> hasSocialGroupId(String socialGroupId) {
    return (root, query, cb) ->
      socialGroupId == null ? null :
        cb.equal(root.join("productId").join("socialGroup").get("id"), socialGroupId);
  }

  public static Specification<ProductSize> hasPromoPriceBetween(Double minPromo, Double maxPromo) {
    return (root, query, cb) -> {
      if (minPromo != null && maxPromo != null)
        return cb.between(root.get("promoPrice"), minPromo, maxPromo);
      if (minPromo != null)
        return cb.greaterThanOrEqualTo(root.get("promoPrice"), minPromo);
      if (maxPromo != null)
        return cb.lessThanOrEqualTo(root.get("promoPrice"), maxPromo);
      return null;
    };
  }

  public static Specification<ProductSize> hasSize(String size) {
    return (root, query, cb) ->
      size == null ? null :
        cb.equal(cb.lower(root.get("size").as(String.class)), size.toLowerCase());
  }

  public static Specification<ProductSize> isOnSale() {
    return (root, query, cb) -> cb.and(
      cb.isNotNull(root.get("promoPrice")),
      cb.lessThan(root.get("promoPrice"), root.get("price"))
    );
  }

  public static Specification<ProductSize> isNewArrival(LocalDateTime since) {
    return (root, query, cb) ->
      since == null ? null :
        cb.greaterThanOrEqualTo(root.get("createdAt"), since);
  }
}
