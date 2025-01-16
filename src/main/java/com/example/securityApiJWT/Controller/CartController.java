package com.example.securityApiJWT.Controller;

import com.example.securityApiJWT.DTO.CartDTO;
import com.example.securityApiJWT.DTO.ItemToCartRequest;
import com.example.securityApiJWT.DTO.ResponseMessageAPI;
import com.example.securityApiJWT.Service.CartService.ICartService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CartController {

    private final ICartService cartService;

    //add items to cart
    @PostMapping("/{userId}")
    public ResponseEntity<CartDTO> addItemToCart(
            @PathVariable Integer userId,
            @RequestBody ItemToCartRequest request
    ) {
        CartDTO cartDto = cartService.addItemToCart(userId, request);
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}/items/{itemId}")
    public ResponseEntity<ResponseMessageAPI> removeItemFromCart(
            @PathVariable Integer userId,
            @PathVariable int itemId
    ) {
        cartService.removeItemFromCart(userId, itemId);
        ResponseMessageAPI response = ResponseMessageAPI.builder()
                .message("Item removed from cart successfully!!")
                .success(true)
                .status(HttpStatus.OK)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ResponseMessageAPI> clearCart(@PathVariable Integer userId) {
        cartService.clearCart(userId);
        ResponseMessageAPI response = ResponseMessageAPI.builder()
                .message("Clearing cart successfully!!")
                .success(true)
                .status(HttpStatus.OK)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<CartDTO> getCart(@PathVariable Integer userId) {
        CartDTO cartDto = cartService.getCart(userId);
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }
}
