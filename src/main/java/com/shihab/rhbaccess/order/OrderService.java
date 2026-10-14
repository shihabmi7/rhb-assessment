package com.shihab.rhbaccess.order;

import com.shihab.rhbaccess.common.ResourceNotFoundException;
import com.shihab.rhbaccess.customer.Customer;
import com.shihab.rhbaccess.customer.CustomerRepository;
import com.shihab.rhbaccess.order.dto.OrderRequest;
import com.shihab.rhbaccess.order.dto.OrderResponse;
import com.shihab.rhbaccess.order.dto.OrderSummary;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderService {

    private final OrderRepository orders;
    private final CustomerRepository customers;

    public OrderService(OrderRepository orders, CustomerRepository customers) {
        this.orders = orders;
        this.customers = customers;
    }

    public OrderResponse create(OrderRequest request) {
        Customer customer = customers.findById(request.customerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer", request.customerId()));
        return OrderResponse.from(orders.save(new Order(customer, request.product(), request.amount())));
    }

    public OrderResponse updateStatus(Long id, OrderStatus status) {
        Order order = orders.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order", id));
        order.setStatus(status);
        return OrderResponse.from(order);
    }

    @Transactional(readOnly = true)
    public List<OrderSummary> summaries(String city) {
        return orders.findSummaries(city == null || city.isBlank() ? null : city.trim());
    }
}
