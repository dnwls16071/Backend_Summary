package com.jwj.springTestCode.spring.repository;

import com.jwj.springTestCode.spring.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockRepository extends JpaRepository<Stock, Long> {

	// 재고 번호로 재고 수량을 조회할 수 있는 메서드 추가
	List<Stock> findAllByProductNumberIn(List<String> productNumber);
}
