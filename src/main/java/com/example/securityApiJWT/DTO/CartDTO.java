package com.example.securityApiJWT.DTO;

import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartDTO {
    private LocalDateTime createdAt;
    private UserDTO user;
    private List<CartItemDTO> items = new ArrayList<>();
}
