package org.example.ecpolyquery.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.queryhandling.QueryHandler;
import org.example.ecpolyquery.entity.Category;
import org.example.ecpolyquery.entity.Customer;
import org.example.ecpolyquery.query.GetAllCategoriesQuery;
import org.example.ecpolyquery.query.GetCategoryByIdQuery;
import org.example.ecpolyquery.query.GetCustomerByEmailQuery;
import org.example.ecpolyquery.repos.CategoryRepository;
import org.example.ecpolyquery.repos.CustomerRepository;
import org.example.polyinformatiquecoreapi.dtoEcommerce.AddressLinkDTO;
import org.example.polyinformatiquecoreapi.dtoEcommerce.CustomerEcommerceDTO;
import org.example.polyinformatiquecoreapi.dtoEcommerce.LikeDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerQueryHandler {

  private final CategoryRepository categoryRepository;
  private final CustomerRepository customerRepository;

  @QueryHandler
  public List<Category> on(GetAllCategoriesQuery query) {
    log.debug("Handling GetAllCategoriesQuery");
    return categoryRepository.findAll();
  }

  @QueryHandler
  public Category on(GetCategoryByIdQuery query) {
    log.debug("Handling GetCategoryByIdQuery: {}", query.getId());
    return categoryRepository.findById(query.getId())
      .orElseThrow(() -> new RuntimeException("Category not found with id: " + query.getId()));
  }

  @QueryHandler
  public CustomerEcommerceDTO handle(GetCustomerByEmailQuery query) {
    Customer customer = customerRepository.findByEmail(query.getMail())
      .orElseThrow(() -> new RuntimeException("Customer not found"));

    // Force chargement des proxys si besoin
    customer.getAddressLinks().forEach(link -> {
      link.getAddress().getId();
    });
    customer.getLikes().size();

    return CustomerEcommerceDTO.builder()
      .id(customer.getId())
      .firstname(customer.getFirstname())
      .lastname(customer.getLastname())
      .email(customer.getEmail())
      .phone(customer.getPhone())
      .build();
  }

}
