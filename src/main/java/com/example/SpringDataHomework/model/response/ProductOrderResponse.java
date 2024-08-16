package com.example.SpringDataHomework.model.response;

import com.example.SpringDataHomework.model.entity.Order;
import com.example.SpringDataHomework.model.entity.Product;

public class ProductOrderResponse {
    private Long id;
    private Order order;
    private Product product;
    private Integer quantity;
}
