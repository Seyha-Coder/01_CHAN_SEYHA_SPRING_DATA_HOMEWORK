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
                .orElseThrow(() -> new CustomNotfoundException("Customer not found"));

        Order order = new Order();
        order.setCustomer(customer);
        order.setOrderDate(new Date());
        order.setStatus(OrderStatus.PENDING);

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

        List<ProductResponse> productList = order.getProductOrders().stream()
                .map(po -> new ProductResponse(
                        po.getProduct().getId(),
                        po.getProduct().getProductName(),
                        po.getProduct().getUnitPrice(),
                        po.getProduct().getDescription()))
                .collect(Collectors.toList());

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

        CustomerResponse customer = customerService.findCustomerById(customerId);

        List<Order> orders = orderRepository.findByCustomer_Id(customerId);

        return orders.stream()
                .map(order -> {
                    List<ProductResponse> productList = order.getProductOrders().stream()
                            .map(productOrder -> {
                                Product product = productOrder.getProduct();
                                return new ProductResponse(product.getId(), product.getProductName(),
                                        product.getUnitPrice(), product.getDescription());
                            }).collect(Collectors.toList());

                    return new OrderResponse(order.getId(), order.getOrderDate(),
                            order.getTotalAmount(), order.getStatus(), productList);
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
