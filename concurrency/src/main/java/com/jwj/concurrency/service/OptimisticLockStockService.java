package com.jwj.concurrency.service;

import com.jwj.concurrency.domain.Stock;
import com.jwj.concurrency.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OptimisticLockStockService {

	private final StockRepository stockRepository;

	// 재고 감소 로직
	/*
	 * 낙관적 락 적용
	 * 업데이트 시 버전 값을 증가시킨다.
	 * 버전 일치 여부를 확인하여 이후 프로세스가 수행된다.
	 */
	@Transactional
	public void decrease(Long productId, Long quantity) {
		Stock stock = stockRepository.findByIdWithOptimisticLock(productId);
		stock.decrease(productId, quantity);
		stockRepository.saveAndFlush(stock);
	}
}
