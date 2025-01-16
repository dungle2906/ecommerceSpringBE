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
public class OrderDTO {
    private Integer orderId;
    private String orderStatus = "PENDING";
    private String paymentStatus = "NOT PAID";
    private int orderAmount;
    private String billingAddress;
    private String billingPhone;
    private String billingName;
    private LocalDateTime orderedDate;
    private LocalDateTime deliveredDate;
    private List<OrderItemDTO> orderItems = new ArrayList<>();
    private String razorPayOrderId;
    private String paymentId;
    private UserDTO user;
}
