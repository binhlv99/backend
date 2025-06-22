package com.trunggame.repository;

import com.trunggame.models.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findAll(Specification<Order> spec, Pageable pageable);

    Page<Order> findByCustomerId(Long customerId, Pageable pageable);

    List<Order> findByStatus(String status);
}