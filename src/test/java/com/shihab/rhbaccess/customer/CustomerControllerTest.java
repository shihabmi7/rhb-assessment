package com.shihab.rhbaccess.customer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.shihab.rhbaccess.common.PageResponse;
import com.shihab.rhbaccess.common.ResourceNotFoundException;
import com.shihab.rhbaccess.customer.dto.CustomerRequest;
import com.shihab.rhbaccess.customer.dto.CustomerResponse;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    CustomerService service;

    @Test
    void getReturnsCustomer() throws Exception {
        when(service.get(1L)).thenReturn(new CustomerResponse(1L, "Amy", "amy@example.com", "KL"));

        mvc.perform(get("/api/customers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Amy"));
    }

    @Test
    void getUnknownReturns404() throws Exception {
        when(service.get(9L)).thenThrow(new ResourceNotFoundException("Customer", 9L));

        mvc.perform(get("/api/customers/9"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Customer not found with id 9"));
    }

    @Test
    void createReturns201() throws Exception {
        when(service.create(any(CustomerRequest.class)))
                .thenReturn(new CustomerResponse(2L, "Bob", "bob@example.com", "Penang"));

        mvc.perform(post("/api/customers").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Bob\",\"email\":\"bob@example.com\",\"city\":\"Penang\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/customers/2"));
    }

    @Test
    void createWithInvalidEmailReturns400() throws Exception {
        mvc.perform(post("/api/customers").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Bob\",\"email\":\"nope\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void searchReturnsPage() throws Exception {
        var page = new PageResponse<>(List.of(new CustomerResponse(1L, "Amy", "amy@example.com", "KL")), 0, 10, 1, 1);
        when(service.search(any(), any(), any())).thenReturn(page);

        mvc.perform(get("/api/customers/search").param("name", "am"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(1))
                .andExpect(jsonPath("$.content[0].name").value("Amy"));
    }
}
