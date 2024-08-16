package com.example.SpringDataHomework.model.request;

import com.example.SpringDataHomework.model.entity.Product;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    private String productName;
    private BigDecimal unitPrice;
    private String description;

    public Product toEntity(){
        return new Product(null,this.productName,this.unitPrice,this.description);
    }
    public Product toEntity(Long id){
        return new Product(id,this.productName,this.unitPrice,this.description);
    }
}
