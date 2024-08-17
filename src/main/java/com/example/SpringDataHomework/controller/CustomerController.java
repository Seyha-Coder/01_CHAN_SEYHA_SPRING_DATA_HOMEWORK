package com.example.SpringDataHomework.controller;

import com.example.SpringDataHomework.model.entity.Customer;
import com.example.SpringDataHomework.model.request.CustomerRequest;
import com.example.SpringDataHomework.model.response.ApiResponse;
import com.example.SpringDataHomework.model.response.CustomerResponse;
import com.example.SpringDataHomework.service.CustomerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/customer")

public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }
    @PostMapping
    public ResponseEntity<?> saveCustomer(@RequestBody CustomerRequest customerRequest){
        CustomerResponse customerResponse = customerService.saveCustomer(customerRequest);
        ApiResponse apiResponse = ApiResponse.builder()
                .message("A new customer is inserted successfully.")
                .status(HttpStatus.CREATED)
                .code("200")
                .payload(customerResponse)
                .build();
        return ResponseEntity.ok(apiResponse);
    }
    @GetMapping("/all")
    public ResponseEntity<?> findAllCustomer(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "5") int pageSize,
            @RequestParam(defaultValue = "customerName") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDirection
    ){
        Page<CustomerResponse> customerResponse = customerService.findAllCustomer(pageNo,pageSize,sortBy,sortDirection);
        ApiResponse apiResponse = ApiResponse.builder()
                .message("Get all customers successfully.")
                .status(HttpStatus.OK)
                .code("200")
                .payload(customerResponse.getContent())
                .build();
        return ResponseEntity.ok(apiResponse);
    }
    @PutMapping("{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable Long id,@RequestBody CustomerRequest customerRequest){
        CustomerResponse customerResponse = customerService.updateCustomer(id,customerRequest);
        ApiResponse apiResponse = ApiResponse.builder()
                .message("Customer id "+id+ " is deleted successfully.")
                .status(HttpStatus.OK)
                .code("200")
                .payload(customerResponse)
                .build();
        return ResponseEntity.ok(apiResponse);
    }
    @GetMapping("{id}")
    public ResponseEntity<?> findCustomerById(@PathVariable Long id){
        CustomerResponse customerResponse = customerService.findCustomerById(id);
        ApiResponse apiResponse = ApiResponse.builder()
                .message("A customer is found.")
                .status(HttpStatus.OK)
                .code("200")
                .payload(customerResponse)
                .build();
        return ResponseEntity.ok(apiResponse);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteCustomerById(@PathVariable Long id){
        customerService.removeCustomerById(id);
        ApiResponse apiResponse = ApiResponse.builder()
                .message("Customer id "+id+" is deleted successfully.")
                .status(HttpStatus.OK)
                .code(null)
                .payload(null)
                .build();
        return ResponseEntity.ok(apiResponse);
    }
}
