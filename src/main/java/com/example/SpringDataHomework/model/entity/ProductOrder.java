package com.example.SpringDataHomework.model.entity;

import com.example.SpringDataHomework.model.response.ProductOrderResponse;
import com.example.SpringDataHomework.model.response.ProductResponse;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "product_order")
public class ProductOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    @JsonManagedReference
    private Product product;

    @Column(name = "quantity")
    private Integer quantity;

    public ProductOrder(Integer quantity, Order order, Product product){
        this.order=order;
        this.product=product;
        this.quantity=quantity;
    }
}
