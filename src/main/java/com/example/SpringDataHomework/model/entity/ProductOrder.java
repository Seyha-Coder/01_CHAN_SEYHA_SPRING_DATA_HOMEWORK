package com.example.SpringDataHomework.model.entity;

import com.example.SpringDataHomework.model.response.ProductOrderResponse;
import com.example.SpringDataHomework.model.response.ProductResponse;
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
    private Product product;

    @Column(name = "quantity")
    private Integer quantity;

//    public ProductOrderResponse toResponse() {
//        ProductResponse productResponse = this.product != null ? new ProductResponse(
//                this.product.getId(),
//                this.product.getProductName(),
//                this.product.getUnitPrice(),
//                this.product.getDescription()
//        ) : null;
//
//        return new ProductOrderResponse(
//                this.id,
//                productResponse,
//                this.quantity
//        );
//    }
}
