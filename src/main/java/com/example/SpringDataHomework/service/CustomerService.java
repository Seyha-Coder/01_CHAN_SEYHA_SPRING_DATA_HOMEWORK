package com.example.SpringDataHomework.service;

import com.example.SpringDataHomework.model.entity.Customer;
import com.example.SpringDataHomework.model.request.CustomerRequest;
import com.example.SpringDataHomework.model.response.CustomerResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;

import java.util.Optional;

public interface CustomerService {
    public CustomerResponse saveCustomer(CustomerRequest customerRequest);
    public Page<Customer> findAllCustomer(int pageNo, int pageSize, String sortBy, String sortDirection);
    public CustomerResponse findCustomerById(Long id);
    public CustomerResponse updateCustomer(Long id, CustomerRequest customerRequest);
    public void removeCustomerById(Long id);
}
