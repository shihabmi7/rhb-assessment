package com.shihab.rhbaccess.customer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    /** Optional filters: a null / blank parameter is ignored. */
    @Query("""
            SELECT c FROM Customer c
            WHERE (:name IS NULL OR LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%')))
              AND (:city IS NULL OR LOWER(c.city) = LOWER(:city))
            """)
    Page<Customer> search(@Param("name") String name, @Param("city") String city, Pageable pageable);
}
