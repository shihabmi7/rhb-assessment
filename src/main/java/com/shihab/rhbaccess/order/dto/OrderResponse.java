package com.shihab.rhbaccess.order.dto;

import com.shihab.rhbaccess.order.Order;
import com.shihab.rhbaccess.order.OrderStatus;
import java.math.BigDecimal;

public record OrderResponse(Long id, Long customerId, String product, BigDecimal amount, OrderStatus status) {

    public static OrderResponse from(Order o) {
        return new OrderResponse(o.getId(), o.getCustomer().getId(), o.getProduct(), o.getAmount(), o.getStatus());
    }
}
