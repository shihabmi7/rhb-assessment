package com.shihab.rhbaccess.order;

import static org.assertj.core.api.Assertions.assertThat;

import com.shihab.rhbaccess.order.dto.OrderSummary;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class OrderRepositoryTest {

    @Autowired
    OrderRepository repository;

    @Test
    void joinReturnsOrderWithCustomerDetails() {
        List<OrderSummary> all = repository.findSummaries(null);

        assertThat(all).hasSize(5);
        assertThat(all.get(0).customerName()).isEqualTo("Alice Tan");
        assertThat(all.get(0).product()).isEqualTo("Laptop");
    }

    @Test
    void joinCanFilterByCity() {
        List<OrderSummary> penang = repository.findSummaries("penang");

        assertThat(penang).hasSize(1);
        assertThat(penang.get(0).customerName()).isEqualTo("Bob Lim");
    }
}
