package com.example.SpringDataHomework.service.serviceImpl;

import com.example.SpringDataHomework.exception.CustomNotfoundException;
import com.example.SpringDataHomework.model.entity.Customer;
import com.example.SpringDataHomework.model.request.CustomerRequest;
import com.example.SpringDataHomework.model.response.CustomerResponse;
import com.example.SpringDataHomework.repository.CustomerRepository;
import com.example.SpringDataHomework.service.CustomerService;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public CustomerResponse saveCustomer(CustomerRequest customerRequest) {
        return customerRepository.save(customerRequest.toEntity()).toResponse();
    }
    @Override
    public Page<CustomerResponse> findAllCustomer(int pageNo, int pageSize, String sortBy, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Customer> customers = customerRepository.findAll(pageable);
        return customers.map(Customer::toResponse);
    }


    @Override
    public CustomerResponse findCustomerById(Long id) {
       CustomerResponse customerResponse = customerRepository.findById(id).orElseThrow(
               ()-> new CustomNotfoundException("Customer with id "+ id + "not found!")
               ).toResponse();
       return customerResponse;
    }

    @Override
    public CustomerResponse updateCustomer(Long id, CustomerRequest customerRequest) {
       customerRepository.findById(id).orElseThrow(
                ()-> new CustomNotfoundException("Customer with id "+ id + "not found!")
        ).toResponse();
        return customerRepository.save(customerRequest.toEntity(id)).toResponseWithOrder();
    }

    @Override
    public void removeCustomerById(Long id) {
        customerRepository.findById(id).orElseThrow(
                ()-> new CustomNotfoundException("Customer with id "+ id + "not found!")
        );
        customerRepository.deleteById(id);
    }
}
