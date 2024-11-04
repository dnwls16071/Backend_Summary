package com.jwj.concurrency.service;

import com.jwj.concurrency.domain.Stock;
import com.jwj.concurrency.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StockService {

	private final StockRepository stockRepository;

	// 재고 감소 로직
	public void decrease(Long productId, Long quantity) {
		Stock stock = stockRepository.findById(productId).orElseThrow();
		stock.decrease(productId, quantity);
		stockRepository.saveAndFlush(stock);
	}
}
