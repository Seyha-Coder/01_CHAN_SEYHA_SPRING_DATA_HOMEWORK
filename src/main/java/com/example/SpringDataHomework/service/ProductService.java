package com.example.SpringDataHomework.service;

import com.example.SpringDataHomework.model.entity.Product;
import com.example.SpringDataHomework.model.request.ProductRequest;
import com.example.SpringDataHomework.model.response.ProductResponse;
import org.springframework.data.domain.Page;

public interface ProductService {
    public ProductResponse saveProduct(ProductRequest productRequest);
    public Page<Product> findAllProduct(int pageNo, int pageSize, String sortBy, String sortDirection);
    public ProductResponse findById(Long id);
    public ProductResponse updateProduct(Long id, ProductRequest productRequest);
    public void deleteById(Long id);
}
