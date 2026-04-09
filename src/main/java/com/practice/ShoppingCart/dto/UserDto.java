package com.practice.ShoppingCart.dto;

import com.practice.ShoppingCart.model.Cart;
import com.practice.ShoppingCart.model.Order;
import lombok.Data;

import java.util.List;

@Data
public class UserDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private CartDto cart;
    private List<OrderDto> orders;
}
