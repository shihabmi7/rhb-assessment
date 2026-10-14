package com.shihab.rhbaccess.order.dto;

import com.shihab.rhbaccess.order.OrderStatus;
import jakarta.validation.constraints.NotNull;

public record OrderStatusRequest(@NotNull OrderStatus status) {
}
