package org.example.polyinformatiquecoreapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class CustomerBlogDTO {
    private String id;
    @NotBlank(message = "Username cannot be blank")
    @Size(min = 5, max = 50, message = "Username must be between 5 and 50 characters")
    private String username;
    @NotBlank(message = "Lastname cannot be blank")
    @Size(min = 5, max = 50, message = "Lastname must be between 5 and 50 characters")
    private String Lastname;
    @NotBlank(message = "Email cannot be blank")
    @Size(min = 5, max = 50, message = "Email must be between 5 and 50 characters")
    private String email;
    @NotBlank(message = "Name cannot be blank")
    @Size(min = 5, max = 50, message = "Name must be between 5 and 50 characters")
    private String name;
    @NotBlank(message = "Phone cannot be blank")
    @Size(min = 5, max = 50, message = "Phone must be between 5 and 50 characters")
    private String phone;
    @NotBlank(message = "Address cannot be blank")
    @Size(min = 5, max = 50, message = "Address must be between 5 and 50 characters")
    private String address;
    @NotEmpty(message = "ArticleIds cannot be empty")
    private List<String> iteamIds;
    @NotEmpty(message = "CommentIds cannot be empty")
    private List<String> commentIds;
}

