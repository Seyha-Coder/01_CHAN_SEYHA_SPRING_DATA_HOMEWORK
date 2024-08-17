package com.example.SpringDataHomework.model.request;

import com.example.SpringDataHomework.model.entity.Customer;
import com.example.SpringDataHomework.model.entity.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRequest {
    @NotNull(message = "Name must not be null!")
    private String customerName;

    @NotNull(message = "Address must not be null")
    private String address;

    @NotNull(message = "Phone number must not be null!")
    private String phone;

    @Pattern(regexp = "")
    @NotNull(message = "Email must not be null!")
    private String email;

    public Customer toEntity() {
        Email emailEntity = new Email(null, this.email.toLowerCase());
        return new Customer(null, this.customerName, this.address, this.phone, emailEntity);
    }

    public Customer toEntity(Long id) {
        Email emailEntity = new Email(id, this.email);
        return new Customer(id, this.customerName, this.address, this.phone, emailEntity);
    }
}
