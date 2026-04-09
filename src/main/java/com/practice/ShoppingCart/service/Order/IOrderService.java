package com.practice.ShoppingCart.service.Order;

import com.practice.ShoppingCart.dto.OrderDto;
import com.practice.ShoppingCart.model.Order;

import java.util.List;

public interface IOrderService {

    Order placeOrder(Long userId);
    OrderDto getOrder(Long orderId);

    List<OrderDto> getUserOrders(Long userId);
}
