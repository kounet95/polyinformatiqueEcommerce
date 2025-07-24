package org.example.polyinformatiquecoreapi.dtoEcommerce;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;


import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateSupplyWithStockDTO {
  private String supplyId;
  private String addressId;
  private String name;

  @NotNull(message = "stocks must not be null")
  @Valid
  private List<StockDTO> stocks;

  private String street;
  private String city;
  private String state;
  private String zip;
  private String country;
  private String appartment;

  private List<AddressLinkDTO> links;

  private LocalDateTime createdAt;
}
