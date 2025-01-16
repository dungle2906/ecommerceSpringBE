package com.example.securityApiJWT.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "product_table")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", nullable = false, unique = true)
    private Integer productId;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "description", length = 1000000)
    private String description;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "discounted_price")
    private double discountedPrice;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "added_date")
    private LocalDateTime addedDate;

    @Column(name = "isEnable", nullable = false, columnDefinition = "bit default 1")
    private boolean isEnable;

    @Column(name = "product_image_name", length = 255)
    private String productImageName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;
}
