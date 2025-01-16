package com.example.securityApiJWT.Repository;

import com.example.securityApiJWT.Model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
