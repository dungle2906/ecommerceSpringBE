package com.example.securityApiJWT.Service.CartService;

import com.example.securityApiJWT.DTO.CartDTO;
import com.example.securityApiJWT.DTO.ItemToCartRequest;
import com.example.securityApiJWT.Exception.ResourceNotFoundException;
import com.example.securityApiJWT.Model.Cart;
import com.example.securityApiJWT.Model.CartItem;
import com.example.securityApiJWT.Model.Product;
import com.example.securityApiJWT.Model.User;
import com.example.securityApiJWT.Repository.CartItemRepository;
import com.example.securityApiJWT.Repository.CartRepository;
import com.example.securityApiJWT.Repository.ProductRepository;
import com.example.securityApiJWT.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements ICartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Autowired
    private final ModelMapper modelMapper;

    @Override
    public CartDTO addItemToCart(Integer userId, ItemToCartRequest itemToCartRequest) {
        Integer productId = itemToCartRequest.getProductId();
        Integer quantity = itemToCartRequest.getQuantity();

        if(quantity <= 0){
            throw new ResourceNotFoundException("Quantity of the product must be greater than 0.");
        }

        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found with given ID!"));
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found!"));

        Cart cart = cartRepository.findByUser(user).orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setCreatedAt(LocalDateTime.now());
            return newCart;
        });

        AtomicReference<Boolean> updated = new AtomicReference<>(false);
        List<CartItem> items = cart.getItems();
        items = items.stream().map(item -> {
            if (Objects.equals(item.getProduct().getProductId(), productId)) {
                item.setQuantity(quantity);
                item.setTotalPrice(quantity * product.getDiscountedPrice());
                updated.set(true);
            }
            return item;
        }).toList();

        if (!updated.get()) {
            CartItem cartItem = CartItem.builder()
                    .quantity(quantity)
                    .totalPrice(quantity * product.getDiscountedPrice())
                    .cart(cart)
                    .product(product)
                    .build();
            cart.getItems().add(cartItem);
        }

        cart.setUser(user);
        Cart updatedCart = cartRepository.save(cart);
        return modelMapper.map(updatedCart, CartDTO.class);
    }

    @Override
    public void removeItemFromCart(Integer userId, Integer itemId) {
        CartItem cartItem1 = cartItemRepository.findById(itemId).orElseThrow(() -> new ResourceNotFoundException("Cart item not found!"));
        cartItemRepository.delete(cartItem1);
    }

    @Override
    public void clearCart(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found!"));
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException("User's cart not found!"));
        cart.getItems().clear();
        cartRepository.save(cart);
    }

    @Override
    public CartDTO getCart(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user not found in database!!"));
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException("Cart of given user not found !!"));
        return modelMapper.map(cart, CartDTO.class);
    }
}
