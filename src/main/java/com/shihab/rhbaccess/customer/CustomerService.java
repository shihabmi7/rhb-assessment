package com.shihab.rhbaccess.customer;

import com.shihab.rhbaccess.common.PageResponse;
import com.shihab.rhbaccess.common.ResourceNotFoundException;
import com.shihab.rhbaccess.customer.dto.CustomerPatchRequest;
import com.shihab.rhbaccess.customer.dto.CustomerRequest;
import com.shihab.rhbaccess.customer.dto.CustomerResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CustomerService {

    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public CustomerResponse get(Long id) {
        return CustomerResponse.from(find(id));
    }

    @Transactional(readOnly = true)
    public PageResponse<CustomerResponse> search(String name, String city, Pageable pageable) {
        return PageResponse.from(repository.search(blankToNull(name), blankToNull(city), pageable)
                .map(CustomerResponse::from));
    }

    public CustomerResponse create(CustomerRequest request) {
        Customer saved = repository.save(new Customer(request.name(), request.email(), request.city()));
        return CustomerResponse.from(saved);
    }

    /** Full replace (PUT). */
    /** Partial update (PATCH). */
    public CustomerResponse patch(Long id, CustomerPatchRequest request) {
        Customer c = find(id);
        if (request.name() != null) c.setName(request.name());
        if (request.email() != null) c.setEmail(request.email());
        if (request.city() != null) c.setCity(request.city());
        return CustomerResponse.from(c);
    }

    public void delete(Long id) {
        repository.delete(find(id));
    }

    private Customer find(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer", id));
    }

    private static String blankToNull(String s) {
        return s == null || s.isBlank() ? null : s.trim();
    }
}
