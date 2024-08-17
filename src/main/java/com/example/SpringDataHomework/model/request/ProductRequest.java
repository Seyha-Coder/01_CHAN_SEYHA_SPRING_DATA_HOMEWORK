package com.example.SpringDataHomework.model.request;

import com.example.SpringDataHomework.model.entity.Product;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    @NotNull(message = "Product name must not be null!")
    private String productName;
    @NotNull(message = "Unit price name must not be null!")
    private BigDecimal unitPrice;
    @NotNull(message = "Description must not be null!")
    private String description;

    public Product toEntity(){
        return new Product(null,this.productName,this.unitPrice,this.description);
    }
    public Product toEntity(Long id){
        return new Product(id,this.productName,this.unitPrice,this.description);
    }
}
