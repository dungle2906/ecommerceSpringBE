package com.example.securityApiJWT.Service.OrderService;

import com.example.securityApiJWT.DTO.InsertOrderRequest;
import com.example.securityApiJWT.DTO.OrderDTO;
import com.example.securityApiJWT.DTO.PageableResponse;
import com.example.securityApiJWT.DTO.UpdateOrderRequest;

import java.util.List;

public interface IOrderService {
    OrderDTO getOrder(Integer orderId);
    OrderDTO addOrder(InsertOrderRequest orderDto);
    void removeOrder(Integer orderId);
    List<OrderDTO> getUserOrders(Integer userId);
    PageableResponse<OrderDTO> getAllOrders(int pageNumber, int pageSize, String sortBy, String sortDir);
    OrderDTO updateOrder(Integer orderId, UpdateOrderRequest request);
    OrderDTO updateOrder(Integer orderId, OrderDTO request);
}
