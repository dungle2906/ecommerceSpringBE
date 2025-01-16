package com.example.securityApiJWT.Controller;

import com.example.securityApiJWT.DTO.*;
import com.example.securityApiJWT.Repository.OrderRepository;
import com.example.securityApiJWT.Service.OrderService.IOrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final IOrderService orderService;

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@Valid @RequestBody InsertOrderRequest request) {
        OrderDTO order = orderService.addOrder(request);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{orderId}")
    public ResponseEntity<ResponseMessageAPI> removeOrder(@PathVariable Integer orderId) {
        orderService.removeOrder(orderId);
        ResponseMessageAPI responseMessage = ResponseMessageAPI.builder()
                .status(HttpStatus.OK)
                .message("order is removed !!")
                .success(true)
                .build();
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<OrderDTO>> getUserOrder(@PathVariable Integer userId) {
        List<OrderDTO> ordersOfUser = orderService.getUserOrders(userId);
        return new ResponseEntity<>(ordersOfUser, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<PageableResponse<OrderDTO>> getOrders(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "orderedDate", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "desc", required = false) String sortDir
    ) {
        PageableResponse<OrderDTO> orders = orderService.getAllOrders(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<OrderDTO> updateOrder(
            @PathVariable("orderId") Integer orderId,
            @RequestBody UpdateOrderRequest request
    ) {
        OrderDTO dto = this.orderService.updateOrder(orderId, request);
        return ResponseEntity.ok(dto);
    }
}
