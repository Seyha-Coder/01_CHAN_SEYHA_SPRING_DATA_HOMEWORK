package com.example.SpringDataHomework.model.entity;

import com.example.SpringDataHomework.model.enums.OrderStatus;
import com.example.SpringDataHomework.model.response.OrderResponse;
import com.example.SpringDataHomework.model.response.ProductResponse;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "order_tb")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;
    @Column(name = "order_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yy-MM-dd")
    private Date orderDate;
    @Column(name = "total_amount")
    private BigDecimal totalAmount;
    private OrderStatus status;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    @JsonIgnore
    private Customer customer;
    @OneToMany(mappedBy = "order")
    private Set<ProductOrder> productOrders;

    public OrderResponse toOrderResponse() {
        OrderResponse response = new OrderResponse();
        response.setId(this.id);
        response.setOrderDate(this.orderDate);
        response.setTotalAmount(this.totalAmount);
        response.setStatus(this.status);


//        Set<ProductResponse> productResponses = this.productOrders.stream()
//                .map(productOrder -> productOrder.getProduct().toProductResponse())
//                .collect(Collectors.toSet());
//        response.getProductOrders(productOrders);

        return response;
    }
}
