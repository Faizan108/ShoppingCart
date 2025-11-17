package com.practice.ShoppingCart.requests;

import com.practice.ShoppingCart.model.Category;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AddProductRequest {

    private Long Id;

    private  String name;
    private  String brand;
    private  int inventory;
    private  String description;
    private BigDecimal price;
    private Category category;
}
