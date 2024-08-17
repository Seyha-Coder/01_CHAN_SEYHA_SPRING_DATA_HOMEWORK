package com.example.SpringDataHomework.model.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
//@AllArgsConstructor
@NoArgsConstructor
public class ProductOrderResponse {
    private Long id;
    private String productName;
    private BigDecimal unitPrice;
    private String description;
    private Integer quantity;

    public ProductOrderResponse(Long id, String productName, BigDecimal unitPrice, String description, Integer quantity) {
        this.id = id;
        this.productName = productName;
        this.unitPrice = unitPrice;
        this.description = description;
        this.quantity = quantity;
    }
}
