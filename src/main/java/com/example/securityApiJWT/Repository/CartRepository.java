package com.example.securityApiJWT.Repository;

import com.example.securityApiJWT.Model.Cart;
import com.example.securityApiJWT.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Integer> {
    Optional<Cart> findByUser(User user);
}
