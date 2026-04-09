package com.practice.ShoppingCart.dto;

import com.practice.ShoppingCart.enums.OrderStatus;
import com.practice.ShoppingCart.model.OrderItem;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class OrderDto {
    private Long orderId;
    private LocalDate orderDate;
    private BigDecimal totalAmount;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    private Set<OrderItemDto> orderItems= new HashSet<>();
}
