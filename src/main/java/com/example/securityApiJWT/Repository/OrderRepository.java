package com.example.securityApiJWT.Repository;

import com.example.securityApiJWT.Model.Order;
import com.example.securityApiJWT.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByUser(User user);
}
