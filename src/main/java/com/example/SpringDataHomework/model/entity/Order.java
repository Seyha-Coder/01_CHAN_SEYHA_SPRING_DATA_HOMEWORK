package com.example.SpringDataHomework.model.entity;

import com.example.SpringDataHomework.model.enums.OrderStatus;
import com.example.SpringDataHomework.model.response.OrderResponse;
import com.example.SpringDataHomework.model.response.ProductOrderResponse;
import com.fasterxml.jackson.annotation.JsonFormat;
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

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<ProductOrder> productOrders = new HashSet<>();

    public OrderResponse toOrderResponse() {
        Set<ProductOrderResponse> productOrderResponses = productOrders.stream()
                .map(po -> new ProductOrderResponse(
                        po.getProduct().getId(),
                        po.getProduct().getProductName(),
                        po.getProduct().getUnitPrice(),
                        po.getProduct().getDescription(),
                        po.getQuantity()))
                .collect(Collectors.toSet());

        return new OrderResponse(this.id, this.orderDate, this.totalAmount, this.status, productOrderResponses);
    }
}
