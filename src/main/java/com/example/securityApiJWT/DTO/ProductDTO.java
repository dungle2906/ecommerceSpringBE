package com.example.securityApiJWT.DTO;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {
    private String title;
    private String description;
    private double price;
    @Size(min = 0, max = 100, message = "Discount must be in range 0-100")
    private double discountedPrice;
    private int quantity;
    private String productImageName;
    private CategoryDTO category;
}
