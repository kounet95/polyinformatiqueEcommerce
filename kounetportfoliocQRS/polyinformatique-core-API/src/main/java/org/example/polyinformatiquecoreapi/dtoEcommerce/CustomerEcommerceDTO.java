package org.example.polyinformatiquecoreapi.dtoEcommerce;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.sql.Update;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Builder
public class CustomerEcommerceDTO {
  private String id;

  @NotBlank(message = "Firstname cannot be blank")
  @Size(min = 2, max = 50, message = "Firstname must be between 2 and 50 characters")
  private String firstname;

  @NotBlank(message = "Lastname cannot be blank")
  @Size(min = 2, max = 50, message = "Lastname must be between 2 and 50 characters")
  private String lastname;

  @NotBlank(message = "Email cannot be blank")
  @Email(message = "Email should be valid")
  private String email;

  @NotBlank(message = "Phone cannot be blank")
  @Size(min = 5, max = 20, message = "Phone must be between 5 and 20 characters")
  private String phone;
  private LocalDateTime createdAt;

}
