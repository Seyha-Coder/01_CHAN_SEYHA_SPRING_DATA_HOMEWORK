package com.example.SpringDataHomework.model.response;

import com.example.SpringDataHomework.model.entity.ProductOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
    private Long id;
    private String productName;
    private BigDecimal unitPrice;
    private String description;
    private Set<ProductOrder> productOrderList;
    public ProductResponse(Long id, String productName, BigDecimal unitPrice, String description){
        this.id=id;
        this.productName=productName;
        this.unitPrice=unitPrice;
        this.description=description;
    }
}
