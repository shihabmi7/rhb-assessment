package com.shihab.rhbaccess.customer;

import com.shihab.rhbaccess.common.PageResponse;
import com.shihab.rhbaccess.customer.dto.CustomerPatchRequest;
import com.shihab.rhbaccess.customer.dto.CustomerRequest;
import com.shihab.rhbaccess.customer.dto.CustomerResponse;
import jakarta.validation.Valid;
import java.net.URI;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public CustomerResponse get(@PathVariable Long id) {
        return service.get(id);
    }

    /** GET /api/customers/search?name=ali&city=Penang&page=0&size=5&sort=name,asc */
    @GetMapping("/search")
    public PageResponse<CustomerResponse> search(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String city,
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return service.search(name, city, pageable);
    }

    @PostMapping
    public ResponseEntity<CustomerResponse> create(@Valid @RequestBody CustomerRequest request) {
        CustomerResponse created = service.create(request);
        return ResponseEntity.created(URI.create("/api/customers/" + created.id())).body(created);
    }

    @PatchMapping("/{id}")
    public CustomerResponse patch(@PathVariable Long id, @Valid @RequestBody CustomerPatchRequest request) {
        return service.patch(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
