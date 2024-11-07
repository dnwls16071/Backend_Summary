package com.jwj.springTestCode.spring.repository;

import com.jwj.springTestCode.spring.entity.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {
}
