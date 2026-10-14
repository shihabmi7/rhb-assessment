package com.shihab.rhbaccess.order;

import com.shihab.rhbaccess.order.dto.OrderRequest;
import com.shihab.rhbaccess.order.dto.OrderResponse;
import com.shihab.rhbaccess.order.dto.OrderStatusRequest;
import com.shihab.rhbaccess.order.dto.OrderSummary;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @GetMapping("/with-customer")
    public List<OrderSummary> withCustomer(@RequestParam(required = false) String city) {
        return service.summaries(city);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse create(@Valid @RequestBody OrderRequest request) {
        return service.create(request);
    }

    @PutMapping("/{id}/status")
    public OrderResponse updateStatus(@PathVariable Long id, @Valid @RequestBody OrderStatusRequest request) {
        return service.updateStatus(id, request.status());
    }
}
