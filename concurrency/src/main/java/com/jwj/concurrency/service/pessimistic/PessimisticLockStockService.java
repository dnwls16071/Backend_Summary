package com.jwj.concurrency.service.pessimistic;

import com.jwj.concurrency.domain.Stock;
import com.jwj.concurrency.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PessimisticLockStockService {

	private final StockRepository stockRepository;

	// 재고 감소 로직
	/*
	 * 비관적 락 적용
	 * 하나의 쓰레드가 락을 획득하고 작업을 수행하는 동안 다른 쓰레드는 락을 획득하려고 시도하나 락을 획득하지 못하고 대기 상태가 된다.
	 */
	@Transactional
	public void decrease(Long productId, Long quantity) {
		Stock stock = stockRepository.findByIdWithPessimisticLock(productId);
		stock.decrease(quantity);
		stockRepository.saveAndFlush(stock);
	}
}
