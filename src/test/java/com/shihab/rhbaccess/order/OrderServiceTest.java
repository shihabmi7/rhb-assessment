package com.shihab.rhbaccess.order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.shihab.rhbaccess.common.ResourceNotFoundException;
import com.shihab.rhbaccess.customer.Customer;
import com.shihab.rhbaccess.customer.CustomerRepository;
import com.shihab.rhbaccess.order.dto.OrderRequest;
import com.shihab.rhbaccess.order.dto.OrderResponse;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    OrderRepository orders;

    @Mock
    CustomerRepository customers;

    @InjectMocks
    OrderService service;

    @Test
    void createStartsAsPending() {
        when(customers.findById(1L)).thenReturn(Optional.of(new Customer("Amy", "amy@example.com", "KL")));
        when(orders.save(any(Order.class))).thenAnswer(inv -> inv.getArgument(0));

        OrderResponse result = service.create(new OrderRequest(1L, "Phone", new BigDecimal("999.00")));

        assertThat(result.status()).isEqualTo(OrderStatus.PENDING);
        assertThat(result.product()).isEqualTo("Phone");
    }

    @Test
    void createFailsForUnknownCustomer() {
        when(customers.findById(5L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.create(new OrderRequest(5L, "Phone", BigDecimal.TEN)))
                .isInstanceOf(ResourceNotFoundException.class);
    }
}
