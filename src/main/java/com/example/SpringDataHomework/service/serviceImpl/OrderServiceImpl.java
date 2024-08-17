package com.example.SpringDataHomework.service.serviceImpl;

import com.example.SpringDataHomework.exception.CustomNotfoundException;
import com.example.SpringDataHomework.model.entity.Customer;
import com.example.SpringDataHomework.model.entity.Order;
import com.example.SpringDataHomework.model.entity.Product;
import com.example.SpringDataHomework.model.entity.ProductOrder;
import com.example.SpringDataHomework.model.enums.OrderStatus;
import com.example.SpringDataHomework.model.request.OrderRequest;
import com.example.SpringDataHomework.model.response.CustomerResponse;
import com.example.SpringDataHomework.model.response.OrderResponse;
import com.example.SpringDataHomework.model.response.ProductOrderResponse;
import com.example.SpringDataHomework.repository.CustomerRepository;
import com.example.SpringDataHomework.repository.OrderRepository;
import com.example.SpringDataHomework.repository.ProductRepository;
import com.example.SpringDataHomework.service.CustomerService;
import com.example.SpringDataHomework.service.OrderService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final CustomerService customerService;

    public OrderServiceImpl(CustomerRepository customerRepository, ProductRepository productRepository, OrderRepository orderRepository, CustomerService customerService) {
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.customerService = customerService;
    }

    @Override
    public OrderResponse saveOrder(Long customerId, List<OrderRequest> requests) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomNotfoundException("Customer not found"));

        Order order = new Order();
        order.setCustomer(customer);
        order.setOrderDate(new Date());
        order.setStatus(OrderStatus.PENDING);
        order.setProductOrders(new HashSet<>()); // Initialize the productOrders set

        BigDecimal totalAmount = BigDecimal.ZERO;

        for (OrderRequest request : requests) {
            Product product = productRepository.findById(request.getProductId())
                    .orElseThrow(() -> new CustomNotfoundException("Product not found"));

            ProductOrder productOrder = new ProductOrder();
            productOrder.setOrder(order);
            productOrder.setProduct(product);
            productOrder.setQuantity(request.getQuantity());

            order.getProductOrders().add(productOrder);

            BigDecimal productTotal = product.getUnitPrice().multiply(BigDecimal.valueOf(request.getQuantity()));
            totalAmount = totalAmount.add(productTotal);
        }

        order.setTotalAmount(totalAmount);
        orderRepository.save(order);

        Set<ProductOrderResponse> productOrderResponses = order.getProductOrders().stream()
                .map(po -> new ProductOrderResponse(
                        po.getProduct().getId(),
                        po.getProduct().getProductName(),
                        po.getProduct().getUnitPrice(),
                        po.getProduct().getDescription(),
                        po.getQuantity()))
                .collect(Collectors.toSet());

        return new OrderResponse(order.getId(), order.getOrderDate(), order.getTotalAmount(), order.getStatus(), productOrderResponses);
    }

    @Override
    public OrderResponse findByOrderId(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new CustomNotfoundException("Order with id " + orderId + " not found."));
        return order.toOrderResponse();
    }

    @Override
    public List<OrderResponse> findByCustomerId(Long customerId) {
        CustomerResponse customer = customerService.findCustomerById(customerId);

        Set<Order> orders = (Set<Order>) orderRepository.findByCustomer_Id(customerId);

        return orders.stream()
                .map(order -> {
                    Set<ProductOrderResponse> productOrderResponses = order.getProductOrders().stream()
                            .map(po -> new ProductOrderResponse(
                                    po.getProduct().getId(),
                                    po.getProduct().getProductName(),
                                    po.getProduct().getUnitPrice(),
                                    po.getProduct().getDescription(),
                                    po.getQuantity()))
                            .collect(Collectors.toSet());

                    return new OrderResponse(order.getId(), order.getOrderDate(),
                            order.getTotalAmount(), order.getStatus(), productOrderResponses);
                })
                .collect(Collectors.toList());
    }

    @Override
    public OrderResponse updateStatus(Long id, OrderStatus status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new CustomNotfoundException("Order with id " + id + " not found."));
        order.setStatus(status);

        order = orderRepository.save(order);

        return order.toOrderResponse();
    }
}
