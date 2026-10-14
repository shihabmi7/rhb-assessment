package com.shihab.rhbaccess.customer.dto;

import com.shihab.rhbaccess.customer.Customer;

public record CustomerResponse(Long id, String name, String email, String city) {

    public static CustomerResponse from(Customer c) {
        return new CustomerResponse(c.getId(), c.getName(), c.getEmail(), c.getCity());
    }
}
