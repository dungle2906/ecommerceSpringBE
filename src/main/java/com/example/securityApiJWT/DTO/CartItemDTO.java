package com.example.securityApiJWT.DTO;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemDTO {
    private int cartItemId;
    private ProductDTO product;
    private int quantity;
    private int totalPrice;
}
