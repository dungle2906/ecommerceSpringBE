package com.example.securityApiJWT.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CategoryDTO {
    @NotBlank(message = "Title is required!")
    @Size(min = 4, message = "Title must be of minimum 4 characters")
    private String title;
    @NotBlank(message = "Description is required!")
    private String description;
}
