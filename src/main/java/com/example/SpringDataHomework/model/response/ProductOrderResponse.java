package com.example.SpringDataHomework.model.response;

import com.example.SpringDataHomework.model.entity.Order;
import com.example.SpringDataHomework.model.entity.Product;

public class ProductOrderResponse {
    private Long id;
    private OrderResponse order;
    private ProductResponse product;
    private Integer quantity;

    public ProductOrderResponse(Long id, ProductResponse productResponse, Integer quantity) {
    }
}
