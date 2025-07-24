package org.example.ecpolyquery.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.polyinformatiquecoreapi.dtoEcommerce.SupplierDTO;

import java.util.List;
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class SupplierPageResponse {
  private List<SupplierDTO> content;
  private int page;
  private int size;
  private int totalPages;
  private long totalElements;

  public SupplierPageResponse(String id, String fullname, String email, String personToContact) {
  }
}
