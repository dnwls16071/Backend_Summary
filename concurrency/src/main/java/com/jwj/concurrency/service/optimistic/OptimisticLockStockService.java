package com.jwj.concurrency.service.optimistic;

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
	 * Update 시기마다 버전 값을 올린다.
	 * 다른 트랙잭션에서 변경 요청 시 이 Version 필드의 일치 여부를 확인하고 만약 일치하지 않는다면 롤백시킨다.
	 */
	@Transactional
	public void decrease(Long productId, Long quantity) {
		Stock stock = stockRepository.findByIdWithOptimisticLock(productId);
		stock.decrease(quantity);
		stockRepository.saveAndFlush(stock);
	}
}
