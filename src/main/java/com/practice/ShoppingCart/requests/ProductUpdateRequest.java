package com.practice.ShoppingCart.requests;

import com.practice.ShoppingCart.model.Category;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductUpdateRequest {

    private Long Id;

    private  String name;
    private  String brand;
    private  int inventory;
    private  String description;
    private BigDecimal price;
    private Category category;
}
