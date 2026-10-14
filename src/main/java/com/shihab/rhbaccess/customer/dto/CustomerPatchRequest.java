package com.shihab.rhbaccess.customer.dto;

import jakarta.validation.constraints.Email;

public record CustomerPatchRequest(String name, @Email String email, String city) {
}
