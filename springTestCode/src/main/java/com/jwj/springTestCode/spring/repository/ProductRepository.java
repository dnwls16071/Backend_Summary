package com.jwj.springTestCode.spring.repository;

import com.jwj.springTestCode.spring.ProductSellingStatus;
import com.jwj.springTestCode.spring.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
	List<Product> findAllBySellingStatusIn(List<ProductSellingStatus> sellingStatuses);
	List<Product> findAllByProductNumberIn(List<String> productNumbers);

	@Query(value = "select p.product_number from Product p order by p.id desc limit 1", nativeQuery = true)
	String findLatestProduct();
}
