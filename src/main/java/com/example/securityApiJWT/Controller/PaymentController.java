package com.example.securityApiJWT.Controller;

import com.example.securityApiJWT.DTO.OrderDTO;
import com.example.securityApiJWT.Service.OrderService.IOrderService;
import com.example.securityApiJWT.Service.UserSerivce.IUserService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@RestController
@SecurityRequirement(name="scheme1")
@RequestMapping("/payments")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class PaymentController {

    private final IOrderService orderService;

    @Value("${razorpay.key}")
    private String key;
    @Value("${razorpay.secret}")
    private String secret;

    @PostMapping("/initiate-payment/{orderId}")
    public ResponseEntity<?> initiatePayment(@PathVariable Integer orderId, Principal principal) {
        OrderDTO ourOrder = this.orderService.getOrder(orderId);

        try {
            RazorpayClient razorpayClient = new RazorpayClient(key, secret);
            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount", ourOrder.getOrderAmount() * 100);
            orderRequest.put("currency", "INR");
            orderRequest.put("receipt", "receipt_orderId");

            Order order = razorpayClient.orders.create(orderRequest);
            ourOrder.setRazorPayOrderId(order.get("id"));
//            ourOrder.setPaymentStatus(order.get("status").toString().toUpperCase());
            this.orderService.updateOrder(ourOrder.getOrderId(), ourOrder);

            System.out.println(order);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "orderId", ourOrder.getOrderId(),
                    "razorpayOrderId", ourOrder.getRazorPayOrderId(),
                    "amount", ourOrder.getOrderAmount(),
                    "paymentStatus", ourOrder.getPaymentStatus()
            ));
        } catch (RazorpayException ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Error in creating order!"));
        }
    }

    @PostMapping("/capture/{orderId}")
    public ResponseEntity<?> verifyAndSavePayment(
            @RequestBody Map<String, Object> data,
            @PathVariable Integer orderId
    ) {
        String razorpayOrderId = data.get("razorpayOrderId").toString();
        String razorpayPaymentId = data.get("razorpayPaymentId").toString();
        String razorpayPaymentSignature = data.get("razorpayPaymentSignature").toString();

        OrderDTO order = this.orderService.getOrder(orderId);
        order.setPaymentStatus("PAID");
        order.setPaymentId(razorpayPaymentId);
        this.orderService.updateOrder(orderId, order);

        try {
            RazorpayClient razorpayClient = new RazorpayClient(key, secret);
            JSONObject options = new JSONObject();
            options.put("razorpay_order_id", razorpayOrderId);
            options.put("razorpay_payment_id", razorpayPaymentId);
            options.put("razorpay_signature", razorpayPaymentSignature);

            boolean b = Utils.verifyPaymentSignature(options, secret);
            if (b) {
                System.out.println("payment signature verified");
                return new ResponseEntity<>(
                        Map.of(
                                "message", "Payment Done",
                                "success", true,
                                "signatureVerified", true
                        )
                        , HttpStatus.OK);
            } else {
                System.out.println("payment signature verified");
                return new ResponseEntity<>(
                        Map.of(
                                "message", "Payment done",
                                "success", true,
                                "signatureVerified", false
                        )
                        , HttpStatus.OK);
            }
        } catch (RazorpayException e) {
            throw new RuntimeException(e);
        }
    }
}
