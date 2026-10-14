package com.shihab.rhbaccess.customer;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;

@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository repository;

    @Test
    void searchFiltersByCityWithPagination() {
        var page = repository.search(null, "Penang", PageRequest.of(0, 1));

        assertThat(page.getTotalElements()).isEqualTo(2);
        assertThat(page.getContent()).hasSize(1);
        assertThat(page.getTotalPages()).isEqualTo(2);
    }

    @Test
    void searchByNameIsCaseInsensitive() {
        var page = repository.search("ALICE", null, PageRequest.of(0, 10));

        assertThat(page.getContent()).extracting(Customer::getEmail).containsExactly("alice@example.com");
    }
}
