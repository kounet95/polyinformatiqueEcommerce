package org.example.ecpolyquery.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import java.util.List;

/**
 * A generic class for pagination responses.
 * This class is used to encapsulate paginated data along with pagination metadata.
 * @param <T> the type of the content
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResponse<T> {
  private List<T> content;
  private int pageNumber;
  private int pageSize;
  private long totalElements;
  private int totalPages;
  private boolean first;
  private boolean last;

  public PageResponse(List<SupplierPageResponse> content, int number, int size, long totalElements) {
  }

  /**
   * Creates a PageResponse from a Spring Data Page.
   * @param page the Spring Data Page
   * @param <T> the type of the content
   * @return a PageResponse
   */
  public static <T> PageResponse<T> from(Page<T> page) {
    return new PageResponse<>(
      (List<SupplierPageResponse>) page.getContent(),
      page.getNumber(),
      page.getSize(),
      page.getTotalElements()
    );
  }
}
