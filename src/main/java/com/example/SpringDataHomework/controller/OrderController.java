package com.example.SpringDataHomework.controller;

import com.example.SpringDataHomework.model.request.OrderRequest;
import com.example.SpringDataHomework.model.response.ApiResponse;
import com.example.SpringDataHomework.model.response.OrderResponse;
import com.example.SpringDataHomework.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/order")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    @PostMapping("{customerId}")
    public ResponseEntity<?> createOrder(
            @PathVariable Long customerId,
            @RequestBody List<OrderRequest> orderRequests) {

        OrderResponse orderResponse = orderService.saveOrder(customerId, orderRequests);

        ApiResponse response = ApiResponse.builder()
                .message("A new order is created successfully.")
                .status(HttpStatus.CREATED)
                .code("201")
                .payload(orderResponse)
                .build();
        return ResponseEntity.ok(response);
    }
    @GetMapping("{orderId}")
    public ResponseEntity<?> findOrderById(
            @PathVariable Long orderId) {

        OrderResponse orderResponse = orderService.findByOrderId(orderId);

        ApiResponse response = ApiResponse.builder()
                .message("Order found.")
                .status(HttpStatus.OK)
                .code("200")
                .payload(orderResponse)
                .build();
        return ResponseEntity.ok(response);
    }
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<?> findByCustomerId(@PathVariable Long customerId) {

       List<OrderResponse> orderResponses = orderService.findByCustomerId(customerId);

        ApiResponse response = ApiResponse.builder()
                .message("Order found.")
                .status(HttpStatus.OK)
                .code("200")
                .payload(orderResponses)
                .build();
        return ResponseEntity.ok(response);
    }
}
