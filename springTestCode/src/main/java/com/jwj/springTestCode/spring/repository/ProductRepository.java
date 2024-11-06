package com.jwj.springTestCode.spring.repository;

import com.jwj.springTestCode.spring.ProductSellingStatus;
import com.jwj.springTestCode.spring.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
	List<Product> findAllBySellingStatusIn(List<ProductSellingStatus> sellingStatuses);
}
