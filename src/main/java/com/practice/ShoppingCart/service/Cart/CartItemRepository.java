package com.practice.ShoppingCart.service.Cart;

import com.practice.ShoppingCart.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    void deleteAllByCartId(Long id);

    @Query("select c from CartItem c where c.id=:cartId")
    List<CartItem> findByCartId(Long cartId);


}