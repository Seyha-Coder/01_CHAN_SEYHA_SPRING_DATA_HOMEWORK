package com.example.SpringDataHomework.model.entity;

import com.example.SpringDataHomework.model.response.ProductResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;
    @Column(name = "product_name")
    private String productName;
    @Column(name = "unit_price")
    private BigDecimal unitPrice;
    private String description;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<ProductOrder> productOrderList;

    public Product(Long id, String productName,BigDecimal unitPrice, String description){
        this.id=id;
        this.productName=productName;
        this.unitPrice=unitPrice;
        this.description=description;
    }
    public ProductResponse toProductResponse(){
//        Set<ProductOrder> productOrders = new HashSet<>();
//        for(ProductOrder productOrder: productOrderList){
//            productOrders.add(productOrder);
//        }
        return new ProductResponse(this.id,this.productName,this.unitPrice, this.description);
    }
}
