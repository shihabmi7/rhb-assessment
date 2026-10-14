package com.shihab.rhbaccess.order.dto;

import com.shihab.rhbaccess.order.OrderStatus;
import java.math.BigDecimal;

public record OrderSummary(Long orderId, String customerName, String city, String product,
                           BigDecimal amount, OrderStatus status) {
}
