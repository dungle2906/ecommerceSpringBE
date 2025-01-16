package com.example.securityApiJWT.Service.CartService;

import com.example.securityApiJWT.DTO.CartDTO;
import com.example.securityApiJWT.DTO.ItemToCartRequest;

public interface ICartService {
    CartDTO addItemToCart(Integer userId, ItemToCartRequest itemToCartRequest);
    void removeItemFromCart(Integer userId, Integer itemId);
    void clearCart(Integer userId);
    CartDTO getCart(Integer userId);
}
