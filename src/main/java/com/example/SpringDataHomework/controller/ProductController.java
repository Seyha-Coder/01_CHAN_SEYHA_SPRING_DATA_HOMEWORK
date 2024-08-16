package com.example.SpringDataHomework.controller;

import com.example.SpringDataHomework.model.entity.Customer;
import com.example.SpringDataHomework.model.entity.Product;
import com.example.SpringDataHomework.model.request.CustomerRequest;
import com.example.SpringDataHomework.model.request.ProductRequest;
import com.example.SpringDataHomework.model.response.ApiResponse;
import com.example.SpringDataHomework.model.response.CustomerResponse;
import com.example.SpringDataHomework.model.response.ProductResponse;
import com.example.SpringDataHomework.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/product")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    @PostMapping
    public ResponseEntity<?> saveProduct(@RequestBody ProductRequest productRequest){
        ProductResponse productResponse = productService.saveProduct(productRequest);
        ApiResponse apiResponse = ApiResponse.builder()
                .message("A new product is inserted successfully.")
                .status(HttpStatus.CREATED)
                .code("200")
                .payload(productResponse)
                .build();
        return ResponseEntity.ok(apiResponse);
    }
    @GetMapping("/all")
    public ResponseEntity<?> findAllProduct(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "5") int pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection
    ){
        Page<Product> product = productService.findAllProduct(pageNo,pageSize,sortBy,sortDirection);
        ApiResponse apiResponse = ApiResponse.builder()
                .message("Get all products successfully.")
                .status(HttpStatus.OK)
                .code("200")
                .payload(product.getContent())
                .build();
        return ResponseEntity.ok(apiResponse);
    }
    @PutMapping("{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id,@RequestBody ProductRequest productRequest){
        ProductResponse response = productService.updateProduct(id,productRequest);
        ApiResponse apiResponse = ApiResponse.builder()
                .message("Product id "+id+ " is deleted successfully.")
                .status(HttpStatus.OK)
                .code("200")
                .payload(response)
                .build();
        return ResponseEntity.ok(apiResponse);
    }
    @GetMapping("{id}")
    public ResponseEntity<?> findProductById(@PathVariable Long id){
        ProductResponse productResponse = productService.findById(id);
        ApiResponse apiResponse = ApiResponse.builder()
                .message("A product with id "+id+" has found.")
                .status(HttpStatus.OK)
                .code("200")
                .payload(productResponse)
                .build();
        return ResponseEntity.ok(apiResponse);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteProductById(@PathVariable Long id){
        productService.deleteById(id);
        ApiResponse apiResponse = ApiResponse.builder()
                .message("Product id "+id+" is deleted successfully.")
                .status(HttpStatus.OK)
                .code(null)
                .payload(null)
                .build();
        return ResponseEntity.ok(apiResponse);
    }
}
