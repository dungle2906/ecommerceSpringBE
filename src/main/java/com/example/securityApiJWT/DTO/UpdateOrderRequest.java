package com.example.securityApiJWT.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateOrderRequest {
    private String orderStatus;
    private String paymentStatus;
    private String billingName;
    private String billingPhone;
    private String billingAddress;
    private LocalDateTime deliveredDate;
}
