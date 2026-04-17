package com.practice.ShoppingCart.service.Cart;

import com.practice.ShoppingCart.dto.CartDto;
import com.practice.ShoppingCart.model.Cart;
import com.practice.ShoppingCart.model.User;

import java.math.BigDecimal;

public interface ICartService {

    Cart getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);


    Cart initializeNewCart(User user);


    Cart getCartByUserId(Long userId);
}
