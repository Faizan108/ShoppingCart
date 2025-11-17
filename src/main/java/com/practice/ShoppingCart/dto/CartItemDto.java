package com.practice.ShoppingCart.dto;

import com.practice.ShoppingCart.model.Product;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItemDto {

    private Long id;
    private BigDecimal unitPrice;
    private int quantity;
    private ProductDto product;
}
