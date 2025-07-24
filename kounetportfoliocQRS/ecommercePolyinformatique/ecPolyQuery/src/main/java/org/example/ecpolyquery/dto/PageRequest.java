package org.example.ecpolyquery.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A generic class for pagination requests.
 * This class is used to encapsulate pagination parameters (page number and page size).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageRequest {
  private int page = 0;
  private int size = 10;

  /**
   * Converts this PageRequest to a Spring Data PageRequest.
   * @return a Spring Data PageRequest
   */
  public org.springframework.data.domain.PageRequest toSpringPageRequest() {
    return org.springframework.data.domain.PageRequest.of(page, size);
  }
}
