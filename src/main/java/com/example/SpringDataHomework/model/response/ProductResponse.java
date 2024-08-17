package com.example.SpringDataHomework.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
//@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
    private Long id;
    private String productName;
    private BigDecimal unitPrice;
    private String description;

    // Add the constructor if it's not already there
    public ProductResponse(Long id, String productName, BigDecimal unitPrice, String description) {
        this.id = id;
        this.productName = productName;
        this.unitPrice = unitPrice;
        this.description = description;
    }
}
