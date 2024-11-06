package com.jwj.springTestCode.spring.repository;

import com.jwj.springTestCode.spring.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
