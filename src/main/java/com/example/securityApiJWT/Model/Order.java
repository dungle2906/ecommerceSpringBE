package com.example.securityApiJWT.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "order_table")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id", nullable = false, unique = true)
    private Integer orderId;

    @Column(name = "order_status", nullable = false, length = 50)
    private String orderStatus;

    @Column(name = "payment_status", nullable = false, length = 50)
    private String paymentStatus;

    @Column(name = "order_amount", nullable = false)
    private double orderAmount;

    @Column(name = "billing_address", length = 1000)
    private String billingAddress;

    @Column(name = "billing_phone", length = 15)
    private String billingPhone;

    @Column(name = "billing_name", length = 255)
    private String billingName;

    @Column(name = "ordered_date")
    private LocalDateTime orderedDate;

    @Column(name = "delivered_date")
    private LocalDateTime deliveredDate;

    @Column(name = "razorpay_order_id", length = 255)
    private String razorPayOrderId;

    @Column(name = "payment_id", length = 255)
    private String paymentId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();
}

