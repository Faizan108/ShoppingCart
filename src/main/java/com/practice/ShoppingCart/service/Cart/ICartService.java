package com.practice.ShoppingCart.service.Cart;

import com.practice.ShoppingCart.dto.CartDto;
import com.practice.ShoppingCart.model.Cart;

import java.math.BigDecimal;

public interface ICartService {

    Cart getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);


    Long initializeNewCart();


    Cart getCartByUserId(Long userId);
}
