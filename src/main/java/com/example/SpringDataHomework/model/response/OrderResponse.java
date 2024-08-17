package com.example.SpringDataHomework.model.response;

import com.example.SpringDataHomework.model.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Data
//@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    private Long id;
    private Date orderDate;
    private BigDecimal totalAmount;
    private OrderStatus status;
    private Set<ProductOrderResponse> productOrders;

    // Constructor with ProductOrderResponse
    public OrderResponse(Long id, Date orderDate, BigDecimal totalAmount, OrderStatus status, Set<ProductOrderResponse> productOrders) {
        this.id = id;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.status = status;
        this.productOrders = productOrders;
    }
}
