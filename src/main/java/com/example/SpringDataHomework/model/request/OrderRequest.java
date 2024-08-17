package com.example.SpringDataHomework.model.request;

import com.example.SpringDataHomework.model.entity.Order;
import com.example.SpringDataHomework.model.entity.ProductOrder;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    @NotNull(message = "Quantity must not be null!")
    private Integer quantity;
    @NotNull(message = "Product ID must not be null!")
    private Long productId;
    public ProductOrder toEntity(Order order, ProductRequest productRequest){
        return new ProductOrder(null, order, productRequest.toEntity(productId), this.quantity);
    }

}
