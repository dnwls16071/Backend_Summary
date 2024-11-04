package com.jwj.concurrency.service;

import com.jwj.concurrency.domain.Stock;
import com.jwj.concurrency.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NamedLockStockService {

	private final StockRepository stockRepository;

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void decrease(Long productId, Long quantity) {
		Stock stock = stockRepository.findById(productId).orElseThrow();
		stock.decrease(quantity);
		stockRepository.saveAndFlush(stock);
	}
}
