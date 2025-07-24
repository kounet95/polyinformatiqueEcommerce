package org.example.polyinformatiquecoreapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

//===============================//
//=========== MEDIA ============//
//===============================//
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class MediaDTO implements Serializable {

    private String id;
    @NotBlank(message = "Url cannot be blank")
    @Size(min = 5, max = 100, message = "Url must be between 5 and 100 characters")
    private String url;
    @NotBlank(message = "File name cannot be blank")
    @Size(min = 5, max = 100, message = "File name must be between 5 and 100 characters")
    private String fileName;
    @NotBlank(message = "File type cannot be blank")
    @Size(min = 5, max = 50, message = "File type must be between 5 and 50 characters")
    private String fileType;
    @NotBlank(message = "Item ID cannot be blank")
    @Size(min = 1, max = 50, message = "Item ID must be between 1 and 50 characters")
    private String itemId;

    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFileType() {
        return fileType;
    }
    public String getItemId() {
        return itemId;
    }
}
