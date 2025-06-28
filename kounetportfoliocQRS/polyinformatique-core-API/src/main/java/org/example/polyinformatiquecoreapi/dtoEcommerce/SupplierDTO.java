package org.example.polyinformatiquecoreapi.dtoEcommerce;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class SupplierDTO {
    private String id;

    @NotBlank(message = "Fullname cannot be blank")
    private String fullname;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Person to contact cannot be blank")
    private String personToContact;
}
