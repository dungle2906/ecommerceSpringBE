package com.example.securityApiJWT.Repository;

import com.example.securityApiJWT.Model.Category;
import com.example.securityApiJWT.Model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query("""
    SELECT p FROM Product p WHERE p.isEnabled = ?1
    """)
    Page<Product> findProductEnabled(Pageable pageable);

    Page<Product> findByTitleContaining(String title, Pageable pageable);

    Page<Product> findByCategory(Category category, Pageable pageable);
}
