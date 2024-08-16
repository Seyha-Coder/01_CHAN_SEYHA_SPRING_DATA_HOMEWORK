package com.example.SpringDataHomework.service.serviceImpl;

import com.example.SpringDataHomework.exception.CustomNotfoundException;
import com.example.SpringDataHomework.model.entity.Product;
import com.example.SpringDataHomework.model.request.ProductRequest;
import com.example.SpringDataHomework.model.response.ProductResponse;
import com.example.SpringDataHomework.repository.ProductRepository;
import com.example.SpringDataHomework.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductResponse saveProduct(ProductRequest productRequest) {
        return productRepository.save(productRequest.toEntity()).toProductResponse();
    }

    @Override
    public Page<Product> findAllProduct(int pageNo, int pageSize, String sortBy, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageRequest = PageRequest.of(pageNo, pageSize, sort);
        return productRepository.findAll(pageRequest);
    }

    @Override
    public ProductResponse findById(Long id) {
        ProductResponse productResponse = productRepository.findById(id).orElseThrow(
                ()-> new CustomNotfoundException("Product with id "+id+ " not found!")
        ).toProductResponse();
        return productResponse;
    }

    @Override
    public ProductResponse updateProduct(Long id, ProductRequest productRequest) {
        productRepository.findById(id).orElseThrow(
                ()-> new CustomNotfoundException("Product with id "+id+ " not found!")
        ).toProductResponse();
        return productRepository.save(productRequest.toEntity(id)).toProductResponse();
    }

    @Override
    public void deleteById(Long id) {
        productRepository.findById(id).orElseThrow(
                ()-> new CustomNotfoundException("Product with id "+id+ " not found!")
        ).toProductResponse();
        productRepository.deleteById(id);
    }
}
