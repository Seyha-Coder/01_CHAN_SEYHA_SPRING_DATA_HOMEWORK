package com.example.SpringDataHomework.service.serviceImpl;

import com.example.SpringDataHomework.exception.CustomNotfoundException;
import com.example.SpringDataHomework.model.entity.Customer;
import com.example.SpringDataHomework.model.entity.Order;
import com.example.SpringDataHomework.model.entity.Product;
import com.example.SpringDataHomework.model.enums.OrderStatus;
import com.example.SpringDataHomework.model.request.OrderRequest;
import com.example.SpringDataHomework.model.response.OrderResponse;
import com.example.SpringDataHomework.model.response.ProductResponse;
import com.example.SpringDataHomework.repository.CustomerRepository;
import com.example.SpringDataHomework.repository.OrderRepository;
import com.example.SpringDataHomework.repository.ProductRepository;
import com.example.SpringDataHomework.service.CustomerService;
import com.example.SpringDataHomework.service.OrderService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final CustomerService customerService;

    public OrderServiceImpl(OrderRepository orderRepository, ProductRepository productRepository, CustomerRepository customerRepository, CustomerService customerService) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
        this.customerService = customerService;
    }

    @Override
    public OrderResponse saveOrder(Long customerId, List<OrderRequest> requests) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        BigDecimal totalAmount = requests.stream()
                .map(request -> productRepository.findById(request.getProductId())
                        .orElseThrow(() -> new RuntimeException("Product not found"))
                        .getUnitPrice().multiply(BigDecimal.valueOf(request.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Order order = new Order();
        order.setCustomer(customer);
        order.setOrderDate(new Date());
        order.setTotalAmount(totalAmount);
        order.setStatus(OrderStatus.PENDING);

        orderRepository.save(order);

        List<ProductResponse> productList = requests.stream()
                .map(request -> {
                    Product product = productRepository.findById(request.getProductId())
                            .orElseThrow(() -> new RuntimeException("Product not found"));
                    return new ProductResponse(product.getId(), product.getProductName(),
                            product.getUnitPrice(), product.getDescription());
                }).collect(Collectors.toList());

        return new OrderResponse(order.getId(), order.getOrderDate(), order.getTotalAmount(), order.getStatus(), productList);
    }

    @Override
    public OrderResponse findByOrderId(Long orderId) {
        OrderResponse orderResponses= orderRepository.findById(orderId).orElseThrow(
                ()-> new CustomNotfoundException("Order with id "+ orderId + " not found.")
        ).toOrderResponse();
        return orderResponses;
    }

    @Override
    public List<OrderResponse> findByCustomerId(Long customerId) {
        customerService.findCustomerById(customerId);
        return orderRepository.findAllById(customerId);
    }
}
