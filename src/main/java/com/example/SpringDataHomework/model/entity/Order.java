package com.example.SpringDataHomework.model.entity;

import com.example.SpringDataHomework.model.enums.OrderStatus;
import com.example.SpringDataHomework.model.response.OrderResponse;
import com.example.SpringDataHomework.model.response.ProductOrderResponse;
import com.example.SpringDataHomework.model.response.ProductResponse;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
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
    private Customer customer;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference
    private Set<ProductOrder> productOrders = new HashSet<>();

//    public OrderResponse toOrderResponse() {
//        OrderResponse response = new OrderResponse();
//        response.setId(this.id);
//        response.setOrderDate(this.orderDate);
//        response.setTotalAmount(this.totalAmount);
//        response.setStatus(this.status);
//
//
////        Set<ProductResponse> productResponses = this.productOrders.stream()
////                .map(productOrder -> productOrder.getProduct().toProductResponse())
////                .collect(Collectors.toSet());
////        response.getProductOrders(productOrders);
//
//        return response;
//    }
public OrderResponse toOrderResponse() {
    OrderResponse response = new OrderResponse();
    response.setId(this.id);
    response.setOrderDate(this.orderDate);
    response.setTotalAmount(this.totalAmount);
    response.setStatus(this.status);
    response.setCustomer(this.customer);

    // Map productOrders to ProductOrderResponse
    Set<ProductOrderResponse> productOrderResponses = this.productOrders.stream()
            .map(productOrder -> new ProductOrderResponse(
                    productOrder.getId(),
                    productOrder.getProduct().toProductResponse(),
                    productOrder.getQuantity()
            ))
            .collect(Collectors.toSet());

    response.setProductOrders(productOrderResponses);

    return response;
}


}
