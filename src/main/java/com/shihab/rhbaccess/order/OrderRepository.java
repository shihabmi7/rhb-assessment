package com.shihab.rhbaccess.order;

import com.shihab.rhbaccess.order.dto.OrderSummary;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("""
            SELECT new com.shihab.rhbaccess.order.dto.OrderSummary(o.id, c.name, c.city, o.product, o.amount, o.status)
            FROM Order o JOIN o.customer c
            WHERE (:city IS NULL OR LOWER(c.city) = LOWER(:city))
            ORDER BY o.id
            """)
    List<OrderSummary> findSummaries(@Param("city") String city);
}
