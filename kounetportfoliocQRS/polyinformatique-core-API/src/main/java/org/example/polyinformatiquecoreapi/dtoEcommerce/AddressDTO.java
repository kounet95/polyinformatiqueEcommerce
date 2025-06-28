package org.example.polyinformatiquecoreapi.dtoEcommerce;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressDTO {

  private String id;

  @NotBlank(message = "Street cannot be blank")
  @Size(min = 2, max = 255, message = "Street must be between 2 and 255 characters")
  private String street;

  @NotBlank(message = "City cannot be blank")
  @Size(min = 2, max = 100, message = "City must be between 2 and 100 characters")
  private String city;

  @NotBlank(message = "State cannot be blank")
  @Size(min = 2, max = 100, message = "State must be between 2 and 100 characters")
  private String state;

  @NotBlank(message = "Zip code cannot be blank")
  @Size(min = 4, max = 10, message = "Zip code must be between 4 and 10 characters")
  private String zip;

  @NotBlank(message = "Country cannot be blank")
  @Size(min = 2, max = 100, message = "Country must be between 2 and 100 characters")
  private String country;

  @PositiveOrZero(message = "Apartment number must be zero or positive")
  private int appartment;

  private List<AddressLinkDTO> links;
}
