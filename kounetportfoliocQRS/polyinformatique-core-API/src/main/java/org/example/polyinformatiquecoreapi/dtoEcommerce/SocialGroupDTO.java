package org.example.polyinformatiquecoreapi.dtoEcommerce;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class SocialGroupDTO {
    private String id;

    @NotBlank(message = "Group name cannot be blank")
    private String name;

    @NotBlank(message = "Region cannot be blank")
    private String region;

    @NotBlank(message = "Country cannot be blank")
    private String country;
}