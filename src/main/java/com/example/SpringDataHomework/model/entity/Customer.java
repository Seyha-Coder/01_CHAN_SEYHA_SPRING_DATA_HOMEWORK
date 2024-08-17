package com.example.SpringDataHomework.model.entity;

import com.example.SpringDataHomework.model.response.CustomerResponse;
import com.example.SpringDataHomework.model.response.OrderResponse;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long id;
    @Column(name = "customer_name")
    private String customerName;
    private String address;
    @Column(name = "phone_number", length = 15)
    private String phoneNumber;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE})
    @JoinColumn(name = "email_id")
    private Email email;
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private Set<Order> orderList;

    public Customer(Long id, String customerName, String address, String phoneNumber, Email email){
        this.id=id;
        this.customerName=customerName;
        this.address=address;
        this.phoneNumber=phoneNumber;
        this.email=email;
    }
    public CustomerResponse toResponse(){
//        Set<OrderResponse> orderResponses = new HashSet<>();
//        for(Order order : orderList){
//            orderResponses.add(order.toOrderResponse());
//        }
        return new CustomerResponse(this.id,this.customerName,this.address,this.phoneNumber,this.email);
    }
}
