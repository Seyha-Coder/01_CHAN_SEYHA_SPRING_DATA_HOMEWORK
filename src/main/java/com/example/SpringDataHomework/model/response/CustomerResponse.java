package com.example.SpringDataHomework.model.response;

import com.example.SpringDataHomework.model.entity.Email;
import com.example.SpringDataHomework.model.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponse {
    private Long id;
    private String customerName;
    private String address;
    private String phoneNumber;
    private Email email;
    private Set<Order> orderList;
    public CustomerResponse(Long id, String customerName, String address, String phoneNumber, Email email){
        this.id=id;
        this.customerName=customerName;
        this.address=address;
        this.phoneNumber= phoneNumber;
        this.email=email;
    }
}
