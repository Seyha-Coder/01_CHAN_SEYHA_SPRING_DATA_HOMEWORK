package com.example.SpringDataHomework.repository;

import com.example.SpringDataHomework.model.entity.Order;
import com.example.SpringDataHomework.model.response.OrderResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomer_Id(Long customerId);
}
