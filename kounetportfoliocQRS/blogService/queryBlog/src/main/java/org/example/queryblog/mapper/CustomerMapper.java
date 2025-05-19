package org.example.queryblog.mapper;


import org.example.polyinformatiquecoreapi.dto.CustomerBlogDTO;
import org.example.queryblog.entite.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerBlogDTO toDTO(Customer utilisateur);
}
