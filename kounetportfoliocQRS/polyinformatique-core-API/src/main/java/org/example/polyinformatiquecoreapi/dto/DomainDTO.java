package org.example.polyinformatiquecoreapi.dto;

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
//========== CATEGORY ==========//
//===============================//

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class DomainDTO implements Serializable {
    private String id;
    @NotBlank(message = "Name cannot be blank")
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    private String name;
    @NotEmpty(message = "Articles cannot be empty")
    private List<String> articles;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getArticles() {
        return articles;
    }
}

