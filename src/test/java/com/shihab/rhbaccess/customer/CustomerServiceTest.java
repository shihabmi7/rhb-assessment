package com.shihab.rhbaccess.customer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.shihab.rhbaccess.common.PageResponse;
import com.shihab.rhbaccess.common.ResourceNotFoundException;
import com.shihab.rhbaccess.customer.dto.CustomerPatchRequest;
import com.shihab.rhbaccess.customer.dto.CustomerRequest;
import com.shihab.rhbaccess.customer.dto.CustomerResponse;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    CustomerRepository repository;

    @InjectMocks
    CustomerService service;

    @Test
    void createSavesAndReturnsCustomer() {
        when(repository.save(any(Customer.class))).thenAnswer(inv -> inv.getArgument(0));

        CustomerResponse result = service.create(new CustomerRequest("Amy", "amy@example.com", "KL"));

        assertThat(result.name()).isEqualTo("Amy");
        assertThat(result.email()).isEqualTo("amy@example.com");
    }

    @Test
    void getThrowsWhenMissing() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.get(99L)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void patchOnlyChangesProvidedFields() {
        Customer existing = new Customer("Amy", "amy@example.com", "KL");
        when(repository.findById(1L)).thenReturn(Optional.of(existing));

        CustomerResponse result = service.patch(1L, new CustomerPatchRequest(null, null, "Penang"));

        assertThat(result.city()).isEqualTo("Penang");
        assertThat(result.name()).isEqualTo("Amy");
    }

    @Test
    void searchPassesNullForBlankFilters() {
        var pageable = PageRequest.of(0, 5);
        when(repository.search(null, "Penang", pageable))
                .thenReturn(new PageImpl<>(List.of(new Customer("Bob", "bob@example.com", "Penang")), pageable, 1));

        PageResponse<CustomerResponse> result = service.search(" ", "Penang", pageable);

        assertThat(result.content()).hasSize(1);
        assertThat(result.totalElements()).isEqualTo(1);
    }

    @Test
    void deleteRemovesCustomer() {
        Customer existing = new Customer("Amy", "amy@example.com", "KL");
        when(repository.findById(1L)).thenReturn(Optional.of(existing));

        service.delete(1L);

        verify(repository).delete(existing);
    }
}
