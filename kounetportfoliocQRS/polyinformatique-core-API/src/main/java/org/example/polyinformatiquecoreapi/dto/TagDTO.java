package org.example.polyinformatiquecoreapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

//===============================//
//============= TAG ============//
//===============================//
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class TagDTO implements Serializable {
    private String id;
    @NotBlank(message = "Name cannot be blank")
    @Size(min = 1, max = 50, message = "Name must be between 1 and 50 characters")
    private String name;
    @NotEmpty(message = "itemIds cannot be empty")
    private List<String> itemIds;
}
