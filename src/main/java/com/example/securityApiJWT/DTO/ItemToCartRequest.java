package com.example.securityApiJWT.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ItemToCartRequest {
    private Integer productId;
    private Integer quantity;
}
